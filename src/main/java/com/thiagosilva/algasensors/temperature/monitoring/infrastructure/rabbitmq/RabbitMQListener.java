package com.thiagosilva.algasensors.temperature.monitoring.infrastructure.rabbitmq;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.thiagosilva.algasensors.temperature.monitoring.api.model.TemperatureLogData;

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

    @SneakyThrows
    @RabbitListener(queues = QUEUE_NAME)
    public void handle(
            @Payload TemperatureLogData temperatureData,
            @Headers Map<String, Object> headers) {
        TSID sensorId = temperatureData.getSensorId();
        Double temperature = temperatureData.getValue();
        log.info("Temperature updated: SensorId {} Temp {}", sensorId, temperature);
        log.info("Headers: {}", headers.toString());

        Thread.sleep(Duration.ofSeconds(5));
    }

}
