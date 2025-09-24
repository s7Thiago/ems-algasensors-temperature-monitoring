package com.thiagosilva.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.thiagosilva.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.thiagosilva.algasensors.temperature.monitoring.domain.service.TemperatureMonitoringService;

import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import static com.thiagosilva.algasensors.temperature.monitoring.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_NAME;

import java.time.Duration;
import java.util.Map;

/*
 * Classe responsável por configurar um consumudor da fila neste microserviço
*/
@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final TemperatureMonitoringService service;

    @SneakyThrows
    @RabbitListener(queues = QUEUE_NAME)
    public void handle(@Payload TemperatureLogData data) {
        service.processTemperatureReading(data);
        Thread.sleep(Duration.ofSeconds(5));
    }

}
