package com.thiagosilva.algasensors.temperature.monitoring.domain.model;

import java.io.Serializable;
import java.util.Objects;

import io.hypersistence.tsid.TSID;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Embeddable
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SensorId implements Serializable {
    private TSID value;

    public SensorId(TSID value) {
        Objects.requireNonNull(value, "Sensor ID must not be null on constructor");
        this.value = value;

    }

    public SensorId(Long value) {
        Objects.requireNonNull(value, "Sensor ID must not be null on constructor");
        this.value = TSID.from(value);

    }

    public SensorId(String value) {
        Objects.requireNonNull(value, "Sensor ID must not be null on constructor");
        this.value = TSID.from(value);

    }

    @Override
    public String toString() {
        return value.toString();
    }

}
