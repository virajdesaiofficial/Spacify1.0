package org.uci.spacifyPortal.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyPortal.services.OccupancyService;
import org.uci.spacifyPortal.utilities.MessageResponse;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/occupancy")
public class OccupancyController {
    @Autowired
    private OccupancyService occupancyService;

    private static final Logger LOG = LoggerFactory.getLogger(RoomController.class);

    @PostMapping("/vacantRooms")
    public ResponseEntity<MessageResponse> getAllMonitoringRoomsWithZeroOccupancy(@RequestBody List<Long> roomId) {
        try {
            LOG.info("Trying to check Occupancy of rooms");
            List<Long> room_ids = this.occupancyService.getRoomsWithZeroOccupancy(roomId);      //rooms with zero occupancy
            MessageResponse messageResponse = new MessageResponse(room_ids.toString(), true);
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }catch(Exception e){

            LOG.error("Error while Trying to check Occupancy of rooms : {}", e.getMessage(),e);
            MessageResponse messageResponse = new MessageResponse("Error while getting occupancy. Please check with the admin", false);
            return new ResponseEntity<>(messageResponse, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

}
