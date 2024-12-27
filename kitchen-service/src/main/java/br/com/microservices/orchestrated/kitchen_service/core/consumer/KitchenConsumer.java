package br.com.microservices.orchestrated.kitchen_service.core.consumer;

import br.com.microservices.orchestrated.kitchen_service.core.service.KitchenService;
import br.com.microservices.orchestrated.kitchen_service.core.utils.JsonUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@AllArgsConstructor
public class KitchenConsumer {

    private final KitchenService kitchenService;
    private final JsonUtil jsonUtil;

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.kitchen-success}"
    )
    public void consumeSuccessEvent(String payload) {
        log.info("Receiving success event {} from kitchen-success topic", payload);
        var event = jsonUtil.toEvent(payload);
        kitchenService.startOrderKitchen(event);
    }

    @KafkaListener(
            groupId = "${spring.kafka.consumer.group-id}",
            topics = "${spring.kafka.topic.kitchen-fail}"
    )
    public void consumeFailEvent(String payload) {
        log.info("Receiving rollback event {} from kitchen-fail topic", payload);
        var event = jsonUtil.toEvent(payload);
        kitchenService.rollbackKitchen(event);
    }
}
