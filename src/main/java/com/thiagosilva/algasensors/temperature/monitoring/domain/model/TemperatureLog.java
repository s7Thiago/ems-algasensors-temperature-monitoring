package com.thiagosilva.algasensors.temperature.monitoring.domain.model;

import java.time.OffsetDateTime;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemperatureLog {

    @Id
    @AttributeOverride(name = "value", 
    column = @Column(name = "id", columnDefinition = "uuid"))
    private TemperatureLogId id;

    @Column(name = "\"value\"") // Evitando conflito de palavra reservada do SQL
    private double value;

    private OffsetDateTime registeredAt;

    @Embedded
    @AttributeOverride(name = "value",
    column = @Column(name = "sensor_id", columnDefinition = "bigint"))
    private SensorId sensorId;
}
