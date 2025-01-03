package br.com.microservices.orchestrated.order_service.core.service;

import br.com.microservices.orchestrated.order_service.core.document.Customer;
import br.com.microservices.orchestrated.order_service.core.document.Event;
import br.com.microservices.orchestrated.order_service.core.document.Order;
import br.com.microservices.orchestrated.order_service.core.dto.OrderRequest;
import br.com.microservices.orchestrated.order_service.core.producer.SagaProducer;
import br.com.microservices.orchestrated.order_service.core.repository.OrderRepository;
import br.com.microservices.orchestrated.order_service.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.UUID;

import static io.micrometer.common.util.StringUtils.isEmpty;

@Service
@AllArgsConstructor
public class OrderService {
    private static final String TRANSACTION_ID_PATTERN = "%s_%s";
    private static final String CURRENT_SOURCE = "ORDER-SERVICE";

    private final OrderRepository repository;
    private final JsonUtil jsonUtil;
    private final SagaProducer producer;
    private final EventService eventService;
    private final CustomerService customerService;

    public Order createOrder(OrderRequest orderRequest) {

        var order = Order.builder()
                .products(orderRequest.getProducts())
                .createdAt(LocalDateTime.now())
                .transactionId(
                        String.format(TRANSACTION_ID_PATTERN, Instant.now().toEpochMilli(), UUID.randomUUID())
                )
                .customer(validateCustomer(orderRequest))
                .build();
        repository.save(order);
        producer.sendEvent(jsonUtil.toJson(createPayload(order)));

        return order;
    }

    private Customer validateCustomer(OrderRequest orderRequest) {
        if(isEmpty(orderRequest.getCpfCustomer())) {
            return null;
        }
        return customerService.findByCpf(orderRequest.getCpfCustomer());
    }

    private Event createPayload(Order order) {
        var event = Event.builder()
                .orderId(order.getId())
                .transactionId(order.getTransactionId())
                .payload(order)
                .createdAt(LocalDateTime.now())
                .build();

        return eventService.save(event);
    }
}
