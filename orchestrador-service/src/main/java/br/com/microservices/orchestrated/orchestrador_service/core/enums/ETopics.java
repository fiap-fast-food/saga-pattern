package br.com.microservices.orchestrated.orchestrador_service.core.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ETopics {

    START_SAGA("start-saga"),
    BASE_ORCHESTRATOR("orchestrator"),
    FINISH_SUCCESS("finish-success"),
    FINISH_FAIL("finish-fail"),
    PRODUCT_VALIDATION_SUCCESS("product-validation-success"),
    PRODUCT_VALIDATION_FAIL("product-validation-fail"),
    PAYMENT_SUCCESS("payment-success"),
    PAYMENT_FAIL("payment-fail"),
    INVENTORY_SUCCESS("inventory-success"),
    INVENTORY_FAIL("inventory-fail"),
    KITCHEN_SUCCESS("kitchen-success"),
    KITCHEN_FAIL("kitchen-fail"),
    NOTIFY_ENDING("notify-ending");

    private final String topic;
}
