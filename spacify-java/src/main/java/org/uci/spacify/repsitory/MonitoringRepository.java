package org.uci.spacify.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacify.entity.MonitoringEntity;

@Repository
public interface MonitoringRepository extends JpaRepository<MonitoringEntity, Long> {
}
