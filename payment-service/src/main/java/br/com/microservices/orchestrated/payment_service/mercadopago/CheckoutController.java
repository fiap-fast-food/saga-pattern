package br.com.microservices.orchestrated.payment_service.mercadopago;

import br.com.microservices.orchestrated.payment_service.core.enums.EPaymentStatus;
import br.com.microservices.orchestrated.payment_service.core.model.Payment;
import br.com.microservices.orchestrated.payment_service.core.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
@AllArgsConstructor
public class CheckoutController {

    private final PaymentService paymentService;

    @GetMapping("/{orderId}/{status}")
    public void updateStatusPayment(@PathVariable String orderId, @PathVariable EPaymentStatus status) {
        paymentService.checkoutPayment(orderId, status);
    }

    @GetMapping("/{orderId}")
    public Payment findByOrderId(@PathVariable String orderId) {
        return paymentService.findByOrderId(orderId);
    }
}