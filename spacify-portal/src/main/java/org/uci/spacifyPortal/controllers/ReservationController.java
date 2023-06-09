package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyLib.dto.ReservationRequest;
import org.uci.spacifyLib.entity.ReservationEntity;
import org.uci.spacifyPortal.services.ReservationService;
import org.uci.spacifyPortal.utilities.MessageResponse;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @GetMapping("/all")
    public List<ReservationEntity> getAllReservations() {
        return this.reservationService.getAllReservations();
    }

    @PostMapping("/create")
    public ResponseEntity<MessageResponse> createRoom(@RequestBody ReservationRequest createRequest) {
        try {
            reservationService.createReservation(createRequest);
            return new ResponseEntity<>(new MessageResponse("Successfully reserved!", true), HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Failed to reserve. Please try again later.", false), HttpStatus.BAD_REQUEST);
        }
    }
}
