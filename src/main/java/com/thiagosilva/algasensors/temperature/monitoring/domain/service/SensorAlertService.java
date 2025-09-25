package com.thiagosilva.algasensors.temperature.monitoring.domain.service;

import org.springframework.stereotype.Service;

import com.thiagosilva.algasensors.temperature.monitoring.api.model.TemperatureLogData;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorId;
import com.thiagosilva.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class SensorAlertService {

    private final SensorAlertRepository repository;

    @Transactional
    public void handleAlert(TemperatureLogData data) {

        repository.findBySensorId(new SensorId(data.getSensorId()))
                .ifPresentOrElse(
                        alert -> {
                            if (alert.getMaxTemperature() != null
                                    && data.getValue().compareTo(alert.getMaxTemperature()) > 0) {
                                log.warn("Alert Max: SensorId {} Temp {}", data.getSensorId(), data.getValue());
                            } else if (alert.getMinTemperature() != null
                                    && data.getValue().compareTo(alert.getMinTemperature()) <= 0) {
                                log.warn("Alert Min: SensorId {} Temp {}", data.getSensorId(), data.getValue());
                            } else {
                                logIgnoredAlert(data);
                            }
                        },
                        () -> { // Quando uma temperatura for recebida de um sensor que não tem alerta
                                // habilitado
                            logIgnoredAlert(data);
                        });

    }

    private void logIgnoredAlert(TemperatureLogData data) {
        log.warn("Alert Ignored: SensorId {} Temp {}", data.getSensorId(), data.getValue());
    }

}
