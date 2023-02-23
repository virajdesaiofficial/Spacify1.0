package org.uci.spacify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacify.entity.ReservationEntity;
import org.uci.spacify.repsitory.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<ReservationEntity> getAllReservatons() {
        return this.reservationRepository.findAll();
    }
}
