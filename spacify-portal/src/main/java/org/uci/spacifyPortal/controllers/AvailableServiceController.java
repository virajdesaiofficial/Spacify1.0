package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacifyLib.entity.ReservationEntity;
import org.uci.spacifyPortal.services.AvailableSlotsService;
import org.uci.spacifyPortal.services.ReservationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/availableSlots")
public class AvailableServiceController {

    @Autowired
    private ReservationService reservationService;

//    @GetMapping("/all")
//    public List<ReservationEntity> getAllReservations() {
//        return this.reservationService.getAllReservations();
//    }

    @Autowired
    private AvailableSlotsService availableSlotsService;

    @GetMapping("/all")
    public List<ReservationEntity> getAllAvailableSlots() {
        return this.availableSlotsService.getAllAvailableSlots();
    }
}
