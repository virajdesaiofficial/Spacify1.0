package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacifyPortal.services.AvailableSlotsService;
import org.uci.spacifyPortal.services.ReservationService;
import org.uci.spacifyPortal.utilities.ReservationSlot;
import java.util.List;


@RestController
@RequestMapping("/api/v1/availableSlots")
public class AvailableServiceController {

    @Autowired
    private ReservationService reservationService;


    @Autowired
    private AvailableSlotsService availableSlotsService;

    @GetMapping("/{roomType}")
    public List<ReservationSlot> getAllAvailableSlots(@PathVariable String roomType) {
        return this.availableSlotsService.getAllAvailableSlots(roomType);
    }
}
