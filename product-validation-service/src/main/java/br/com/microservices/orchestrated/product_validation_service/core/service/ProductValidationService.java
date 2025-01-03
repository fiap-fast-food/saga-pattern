package br.com.microservices.orchestrated.product_validation_service.core.service;


import br.com.microservices.orchestrated.product_validation_service.config.exception.ValidationException;
import br.com.microservices.orchestrated.product_validation_service.core.dto.Event;
import br.com.microservices.orchestrated.product_validation_service.core.dto.History;
import br.com.microservices.orchestrated.product_validation_service.core.dto.OrderProducts;
import br.com.microservices.orchestrated.product_validation_service.core.model.Product;
import br.com.microservices.orchestrated.product_validation_service.core.model.Validation;
import br.com.microservices.orchestrated.product_validation_service.core.producer.KafkaProducer;
import br.com.microservices.orchestrated.product_validation_service.core.repository.ProductRepository;
import br.com.microservices.orchestrated.product_validation_service.core.repository.ValidationRepository;
import br.com.microservices.orchestrated.product_validation_service.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

import static br.com.microservices.orchestrated.product_validation_service.core.enums.ESagaStatus.*;
import static org.springframework.util.ObjectUtils.isEmpty;


@Slf4j
@Service
@AllArgsConstructor
public class ProductValidationService {

    private static final String CURRENT_SOURCE = "PRODUCT_VALIDATION_SERVICE";

    private final JsonUtil jsonUtil;
    private final KafkaProducer kafkaProducer;
    private final ProductRepository productRepository;
    private final ValidationRepository validationRepository;


    public void validateExistingProducts(Event event) {
        try {
            checkCurrentValidation(event);
            createValidation(event, true);
            handleSuccess(event);

        } catch (Exception e) {
            log.error("Error trying to validate products: ", e);
            handleFailCurrentNotExecuted(event, e.getMessage());
        }

        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }

    public void rollbackEvent(Event event) {
        changeValidationToFail(event);
        event.setStatus(FAIL);
        event.setSource(CURRENT_SOURCE);
        addHistory(event, "Rollback executed on product validation!");
        kafkaProducer.sendEvent(jsonUtil.toJson(event));
    }

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    private void validateProductsInformed(Event event) {
        if(isEmpty(event.getPayload()) || isEmpty(event.getPayload().getProducts())) {
            throw new ValidationException("Product list is empty!");
        }
        if(isEmpty(event.getPayload().getId()) || isEmpty(event.getPayload().getTransactionId())) {
            throw new ValidationException("OrderID and TransactionID must be informed!");
        }
    }

    private void checkCurrentValidation(Event event) {
        validateProductsInformed(event);
        if(Boolean.TRUE.equals(validationRepository.existsByOrderIdAndTransactionId(
                event.getOrderId(), event.getTransactionId()))) {
            throw new ValidationException("There's another transactionId for this validation.");
        }

        event.getPayload().getProducts().forEach(product -> {
        validateProductInformed(product);
        validateExistingProduct(product.getProduct().getCode());
        });

    }

    private void validateProductInformed(OrderProducts products) {
        if(isEmpty(products.getProduct()) || isEmpty(products.getProduct().getCode())) {
            throw new ValidationException("product must be informed!");
        }
    }

    private void validateExistingProduct(String code) {
        if(Boolean.FALSE.equals(productRepository.existsByCode(code))) {
            throw new ValidationException("Product does not exists in database!");
        }
    }

    private void createValidation(Event event, boolean success) {
        var validation = Validation.builder()
                .orderId(event.getPayload().getId())
                .transactionId(event.getTransactionId())
                .success(success)
                .build();

        validationRepository.save(validation);
    }

    private void handleSuccess(Event event) {
        event.setStatus(SUCCESS);
        event.setSource(CURRENT_SOURCE);
        addHistory(event, "Products are validated successfully");
    }

    private void addHistory(Event event, String message) {
        var history = History.builder()
                .source(event.getSource())
                .status(event.getStatus())
                .message(message)
                .createdAt(LocalDateTime.now())
                .build();

        event.addToHistory(history);
    }

    private void handleFailCurrentNotExecuted(Event event, String message) {
        event.setStatus(ROLLBACK_PENDING);
        event.setSource(CURRENT_SOURCE);
        addHistory(event, "Fail to validate products: ".concat(message));
    }


    private void changeValidationToFail(Event event) {
        validationRepository.findByOrderIdAndTransactionId(event.getPayload().getId(), event.getTransactionId())
                .ifPresentOrElse(validation -> {
                    validation.setSuccess(false);
                    validationRepository.save(validation);
                },
                () -> createValidation(event, false));
    }

}
