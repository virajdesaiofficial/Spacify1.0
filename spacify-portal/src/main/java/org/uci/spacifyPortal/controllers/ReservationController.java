package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyLib.dto.ReservationRequest;
import org.uci.spacifyLib.repository.ReservationRepository;
import org.uci.spacifyPortal.services.ReservationService;
import org.uci.spacifyLib.entity.ReservationEntity;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepository reservationRepository;

    @GetMapping("/all")
    public List<ReservationEntity> getAllReservations() {
        return this.reservationService.getAllReservations();
    }

    @PostMapping("/create")
    public ResponseEntity<String> createRoom(@RequestBody ReservationRequest createRequest) {
        try {
            reservationService.createReservation(createRequest);
            return new ResponseEntity<>("Successful", HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>("Failure", HttpStatus.BAD_REQUEST);
        }
    }
}
