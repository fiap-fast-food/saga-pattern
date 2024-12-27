package br.com.microservices.orchestrated.orchestrador_service.core.enums;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum ESagaStatus {
    @JsonProperty("SUCCESS")
    SUCCESS,
    @JsonProperty("ROLLBACK_PENDING")
    ROLLBACK_PENDING,
    @JsonProperty("FAIL")
    FAIL
}
