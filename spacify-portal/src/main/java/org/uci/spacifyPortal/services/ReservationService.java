package org.uci.spacifyPortal.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.dto.ReservationRequest;
import org.uci.spacifyLib.entity.ReservationEntity;
import org.uci.spacifyLib.repository.ReservationRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationRepository reservationRepository;

    private static final Logger LOG = LoggerFactory.getLogger(ReservationService.class);

    public List<ReservationEntity> getAllReservations() {
        return this.reservationRepository.findAll();
    }

    public boolean createReservation(ReservationRequest reservationRequest) throws Exception {

        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            ReservationEntity reservationEntity = new ReservationEntity();
            reservationEntity.setReservation_id(-1L);
            reservationEntity.setRoomId((long) reservationRequest.getReservedRoomId());
            reservationEntity.setGuests(reservationRequest.getNumberOfGuests());
            reservationEntity.setUser_id(reservationRequest.getReservedBy());
            reservationEntity.setTimeTo(LocalDateTime.parse(reservationRequest.getTimeTo(), formatter));
            reservationEntity.setTimeFrom(LocalDateTime.parse(reservationRequest.getTimeFrom(), formatter));

            reservationRepository.save(reservationEntity);
            LOG.info("reservation made successfully for room: {} from time : {} to time : {}.", reservationEntity.getRoomId(), reservationEntity.getTimeFrom(), reservationEntity.getTimeTo());
            return true;
        }catch(Exception e){

            LOG.error("Error while making reservation with error message: {}", e.getMessage(), e);
            throw new Exception("Unable to create reservation");
        }

    }
}
