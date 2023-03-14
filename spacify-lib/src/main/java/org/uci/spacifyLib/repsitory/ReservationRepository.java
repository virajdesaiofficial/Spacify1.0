package org.uci.spacifyLib.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.ReservationEntity;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {
    public List<ReservationEntity> findAllByCalculatedIs(boolean isCalculated);

    List<ReservationEntity> findByroomIdIn(List<Long> roomId);
//    ReservationEntity findBytimeFromStartingWith(String booking_slot_date);
//    ReservationEntity findBytimeFromBetween(LocalDateTime booking_slot_date_start_time, LocalDateTime booking_slot_date_end_time);
      List<ReservationEntity> findBytimeFromBetweenAndRoomIdIn(LocalDateTime booking_slot_date_start_time, LocalDateTime booking_slot_date_end_time, List<Long> roomId);
}
