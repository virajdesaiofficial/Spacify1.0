package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.dto.ReservationRequest;
import org.uci.spacifyLib.entity.ReservationEntity;
import org.uci.spacifyLib.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<ReservationEntity> getAllReservations() {
        return this.reservationRepository.findAll();
    }

    public boolean createReservation(ReservationRequest reservationRequest) throws Exception {

        try {
            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setReservation_id(-1L);
            reservationEntity.setRoomId(Long.valueOf(reservationRequest.getReservedRoomId()));
            reservationEntity.setGuests(5);
            reservationEntity.setUser_id(reservationRequest.getReservedBy());
            reservationEntity.setTimeTo(LocalDateTime.parse(reservationRequest.getTimeTo()));
            reservationEntity.setTimeFrom(LocalDateTime.parse(reservationRequest.getTimeFrom()));
            reservationRepository.save(reservationEntity);
            return true;
        }catch(Exception e){

            throw new Exception("Unable to create reservation");
        }

    }
}
