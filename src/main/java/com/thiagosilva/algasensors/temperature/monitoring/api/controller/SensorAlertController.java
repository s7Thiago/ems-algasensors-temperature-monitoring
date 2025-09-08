package com.thiagosilva.algasensors.temperature.monitoring.api.controller;

import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.thiagosilva.algasensors.temperature.monitoring.api.model.SensorAlertInput;
import com.thiagosilva.algasensors.temperature.monitoring.api.model.SensorAlertOutput;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorAlertId;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorId;
import com.thiagosilva.algasensors.temperature.monitoring.domain.repository.SensorAlertRepository;

import io.hypersistence.tsid.TSID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/sensors/{sensorId}/alert")
@RequiredArgsConstructor
public class SensorAlertController {

    private final SensorAlertRepository repository;

    @GetMapping
    public SensorAlertOutput find(@PathVariable("sensorId") TSID sensorId) {

        SensorAlert alert = repository.findBySensorId(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Sensor alert for sensor %s not found", sensorId.toString())));

        return SensorAlertOutput.builder()
                .sensorId(alert.getSensorId().getValue())
                .minTemperature(alert.getMinTemperature())
                .maxTemperature(alert.getMaxTemperature())
                .build();
    }

    @PutMapping
    public SensorAlertOutput saveOrUpdate(
            @PathVariable("sensorId") TSID sensorId,
            @RequestBody SensorAlertInput input) {

        SensorAlert alert = repository.findBySensorId(new SensorId(sensorId))
                .orElse(SensorAlert.builder()
                        .id(new SensorAlertId(UUID.randomUUID()))
                        .sensorId(new SensorId(sensorId))
                        .build());

        alert.setMinTemperature(input.getMinTemperature());
        alert.setMaxTemperature(input.getMaxTemperature());
        alert = repository.saveAndFlush(alert);

        return SensorAlertOutput.builder()
                .sensorId(alert.getSensorId().getValue())
                .minTemperature(alert.getMinTemperature())
                .maxTemperature(alert.getMaxTemperature())
                .build();
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void remove(@PathVariable("sensorId") TSID sensorId) {

        SensorAlert alert = repository.findBySensorId(new SensorId(sensorId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("Sensor alert for sensor %s not found", sensorId.toString())));

        repository.delete(alert);
        repository.flush();
    }
}
