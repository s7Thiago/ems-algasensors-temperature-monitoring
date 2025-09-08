package com.thiagosilva.algasensors.temperature.monitoring.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorId;
import com.thiagosilva.algasensors.temperature.monitoring.domain.model.SensorMonitoring;

@Repository
public interface SensorMonitoringRepository extends JpaRepository<SensorMonitoring, SensorId> {

    
}
