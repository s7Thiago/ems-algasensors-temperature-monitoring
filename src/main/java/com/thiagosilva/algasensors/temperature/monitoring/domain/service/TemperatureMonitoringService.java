package com.thiagosilva.algasensors.temperature.monitoring.domain.service;

import java.time.OffsetDateTime;

import org.springframework.stereotype.Service;

import com.thiagosilva.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorId;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorMonitoring;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.TemperatureLogId;
import com.thiagosilva.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;
import com.thiagosilva.algasensors.temperature.monitoring.domain.repository.SensorMonitoringRepository;
import com.thiagosilva.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class TemperatureMonitoringService {

    private final SensorAlertRepository sensorAlertRepository;

    private final SensorMonitoringRepository sensorMonitoringRepository;
    private final TemperatureLogRepository temperatureLogRepository;

    @Transactional
    public void processTemperatureReading(TemperatureLogData data) {

        if (data.getValue() == 10.5) {
            throw new RuntimeException("Simulated processing error");
        }

        sensorMonitoringRepository.findById(new SensorId(data.getSensorId()))
                .ifPresentOrElse(sensor -> handleSensorMonitoring(data, sensor), () -> logIgnoredTemperature(data));

    }

    /*
     * Fluxo quando o sensor recebido via registro de temperatura na queue existe
     */
    private void handleSensorMonitoring(TemperatureLogData data, SensorMonitoring sensor) {

        if (sensor.isEnabled()) {
            sensor.setLastTemperature(data.getValue());
            sensor.setUpdatedAt(OffsetDateTime.now());

            TemperatureLog logTemp = TemperatureLog.builder()
                    .id(new TemperatureLogId(data.getId()))
                    .value(data.getValue())
                    .registeredAt(data.getRegisteredAt())
                    .sensorId(new SensorId(data.getSensorId()))
                    .build();

            temperatureLogRepository.save(logTemp);
            sensorMonitoringRepository.save(sensor);

            log.info("Temperature updated: SensorID {} Temp {}", data.getSensorId(), data.getValue());
        } else {
            logIgnoredTemperature(data);
        }
    }

    /*
     * Fluxo para quando um sensor que foi recebido via mensagem na queue não for
     * encontrado
     */
    private void logIgnoredTemperature(TemperatureLogData data) {
        log.info("Temperature ignored: SensorID {} Temp {}", data.getSensorId(), data.getValue());
    }

}
