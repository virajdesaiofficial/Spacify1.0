package org.uci.spacifyPortal.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyPortal.entity.MonitoringEntity;

@Repository
public interface MonitoringRepository extends JpaRepository<MonitoringEntity, Long> {
}
