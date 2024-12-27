package br.com.microservices.orchestrated.payment_service.config.exception;

public class MercadoPagoException extends RuntimeException {
    public MercadoPagoException(String message) {
        super(message);
    }
}
