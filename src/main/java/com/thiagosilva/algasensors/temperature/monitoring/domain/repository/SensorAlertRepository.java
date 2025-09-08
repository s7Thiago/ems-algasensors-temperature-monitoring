package com.thiagosilva.algasensors.temperature.monitoring.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorAlert;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorAlertId;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorId;

@Repository
public interface SensorAlertRepository extends JpaRepository<SensorAlert, SensorAlertId>{

    Optional<SensorAlert> findBySensorId(SensorId sensorId);

}
