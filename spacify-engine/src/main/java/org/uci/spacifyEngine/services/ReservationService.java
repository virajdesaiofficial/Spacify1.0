package org.uci.spacifyEngine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.ReservationEntity;
import org.uci.spacifyLib.repsitory.ReservationRepository;

import java.util.List;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<ReservationEntity> getAllReservatons() {
        return this.reservationRepository.findAll();
    }
}