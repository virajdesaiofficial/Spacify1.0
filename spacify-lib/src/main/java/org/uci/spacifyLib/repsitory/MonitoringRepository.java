package org.uci.spacifyLib.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.MonitoringEntity;

@Repository
public interface MonitoringRepository extends JpaRepository<MonitoringEntity, Long> {
}
