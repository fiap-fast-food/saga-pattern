package br.com.microservices.orchestrated.orchestrador_service.core.dto;

import br.com.microservices.orchestrated.orchestrador_service.core.enums.EEventSource;
import br.com.microservices.orchestrated.orchestrador_service.core.enums.ESagaStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class History {

    private EEventSource source;
    private ESagaStatus status;
    private String message;
    private LocalDateTime createdAt;
}
