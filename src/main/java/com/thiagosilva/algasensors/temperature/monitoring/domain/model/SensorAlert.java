package com.thiagosilva.algasensors.temperature.monitoring.domain.model;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SensorAlert {

    @Id
    @AttributeOverride(name = "value",
    column = @Column(name = "id", columnDefinition = "uuid"))
    private SensorAlertId id;

    @Embedded
    @AttributeOverride(name = "value",
    column = @Column(name = "sensor_id", columnDefinition = "bigint"))
    private SensorId sensorId;

    @Column(name = "min_temperature")
    private Double minTemperature;

    @Column(name = "max_temperature")
    private Double maxTemperature;

}
