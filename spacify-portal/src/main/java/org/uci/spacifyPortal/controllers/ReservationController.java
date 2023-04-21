package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacifyLib.repsitory.IncentiveRepository;
import org.uci.spacifyPortal.services.ReservationService;
import org.uci.spacifyLib.entity.ReservationEntity;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {
    @Autowired
    private ReservationService reservationService;

    @Autowired
    private IncentiveRepository incentiveRepository;

    @GetMapping("/all")
    public List<ReservationEntity> getAllReservations() {
        return this.reservationService.getAllReservations();
    }
}
