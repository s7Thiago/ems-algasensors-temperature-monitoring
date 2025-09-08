package com.thiagosilva.algasensors.temperature.monitoring.domain.model;

import java.io.Serializable;
import java.util.UUID;

import jakarta.persistence.Embeddable;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor
public class SensorAlertId implements Serializable {

    private UUID value;

    public SensorAlertId(UUID value) {
        this.value = value;
    }

    public SensorAlertId(String value) {
        this.value = UUID.fromString(value);
    }

    @Override
    public String toString() {
        return value.toString();
    }

    

}
