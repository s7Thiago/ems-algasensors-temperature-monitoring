package com.thiagosilva.algasensors.temperature.monitoring.api.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.thiagosilva.algasensors.temperature.monitoring.api.model.TemperatureLogOutput;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorId;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.thiagosilva.algasensors.temperature.monitoring.domain.repository.TemperatureLogRepository;

import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/sensors/{sensorId}/temperatures")
@RequiredArgsConstructor
public class TemperatureLogController {

    private final TemperatureLogRepository repository;

    @GetMapping("")
    public Page<TemperatureLogOutput> search(
            @PathVariable("sensorId") TSID sensorId,
            @PageableDefault Pageable pageable) {
        Page<TemperatureLog> logs = repository.findAllBySensorId(new SensorId(sensorId), pageable);

        return logs.map(log -> TemperatureLogOutput.builder()
                .id(log.getId().getValue())
                .value(log.getValue())
                .registeredAt(log.getRegisteredAt())
                .sensorId(log.getSensorId().getValue())
                .build());
    }

}
