package org.uci.spacifyLib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.MonitoringEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface MonitoringRepository extends JpaRepository<MonitoringEntity, Long> {
    List<MonitoringEntity> findAllByTimestampFromAfterAndTippersSpaceIdAndMacAddressIn(LocalDateTime localDateTime, Integer tippersSpaceIds, List<String> macAddressList);
}
