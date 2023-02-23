package org.uci.spacify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacify.entity.ReservationEntity;
import org.uci.spacify.services.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @GetMapping("/all")
    public List<ReservationEntity> getAllReservations() {
        return this.reservationService.getAllReservatons();
    }
}
