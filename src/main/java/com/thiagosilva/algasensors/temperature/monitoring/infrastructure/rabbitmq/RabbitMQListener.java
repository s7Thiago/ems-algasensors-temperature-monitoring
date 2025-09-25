package com.thiagosilva.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import static com.thiagosilva.algasensors.temperature.monitoring.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_ALERT_NAME;
import static com.thiagosilva.algasensors.temperature.monitoring.infrastructure.rabbitmq.RabbitMQConfig.QUEUE_PROCESS_TEMPERATURE_NAME;

import java.time.Duration;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.thiagosilva.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.thiagosilva.algasensors.temperature.monitoring.domain.service.TemperatureMonitoringService;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/*
 * Classe responsável por configurar um consumudor da fila neste microserviço
*/
@Slf4j
@Component
@RequiredArgsConstructor
public class RabbitMQListener {

    private final TemperatureMonitoringService service;

    /*
     * O concurrency Faz com que o listener use de 2 a 3 threads para ler as
     * mensagens da fila, ou seja, ele poderá ler mais de 1 mensagem da fila
     * por vez
     */
    @SneakyThrows
    @RabbitListener(queues = QUEUE_PROCESS_TEMPERATURE_NAME, concurrency = "2-3")
    public void handleTemepratureProcessing(@Payload TemperatureLogData data) {
        service.processTemperatureReading(data);
        Thread.sleep(Duration.ofSeconds(5));
    }

    @SneakyThrows
    @RabbitListener(queues = QUEUE_ALERT_NAME, concurrency = "2-3")
    public void handleAlertMessage(@Payload TemperatureLogData data) {
        log.info("Alerting: sensorId {} Temp {}", data.getSensorId(), data.getValue());
        Thread.sleep(Duration.ofSeconds(5));
    }

}
