package com.thiagosilva.algasensors.temperature.monitoring.api.model;

import io.hypersistence.tsid.TSID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SensorAlertOutput {
    private TSID sensorId;
    private Double minTemperature;
    private Double maxTemperature;
}
