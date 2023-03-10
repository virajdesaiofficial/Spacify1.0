package org.uci.spacifyLib.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.ReservationEntity;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
}
