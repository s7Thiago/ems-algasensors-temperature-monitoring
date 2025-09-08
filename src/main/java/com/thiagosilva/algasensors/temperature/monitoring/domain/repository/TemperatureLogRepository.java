package com.thiagosilva.algasensors.temperature.monitoring.domain.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorId;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.TemperatureLog;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.TemperatureLogId;

@Repository
public interface TemperatureLogRepository extends JpaRepository<TemperatureLog, TemperatureLogId> {

    Page<TemperatureLog> findAllBySensorId(SensorId sensorId, Pageable pageable);

}
