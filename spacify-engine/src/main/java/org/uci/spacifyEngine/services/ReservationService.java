package org.uci.spacifyEngine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.ReservationEntity;
import org.uci.spacifyLib.repository.ReservationRepository;

import java.util.List;
import java.util.Objects;

@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;

    public List<ReservationEntity> getAllReservations() {
        return this.reservationRepository.findAll();
    }

    public List<ReservationEntity> getUnCalculatedReservations() {
        return this.reservationRepository.findAllByCalculatedIs(false);
    }

    public void markReservationsAsCalculated(List<ReservationEntity> reservationEntityList) {
        if (Objects.nonNull(reservationEntityList) && !reservationEntityList.isEmpty())
            this.reservationRepository.saveAll(reservationEntityList);
    }
}