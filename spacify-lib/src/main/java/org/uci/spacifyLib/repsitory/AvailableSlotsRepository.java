package org.uci.spacifyLib.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.uci.spacifyLib.entity.AvailableSlotsEntity;
import org.uci.spacifyLib.entity.ReservationEntity;

import java.util.List;

public interface AvailableSlotsRepository extends JpaRepository<AvailableSlotsEntity, Long> {
    List<AvailableSlotsEntity> findByroomType(String room_type);
}
