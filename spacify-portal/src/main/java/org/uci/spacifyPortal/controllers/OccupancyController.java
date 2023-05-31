package org.uci.spacifyPortal.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyPortal.services.OccupancyService;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/occupancy")
public class OccupancyController {
    @Autowired
    private OccupancyService occupancyService;

    private static final Logger LOG = LoggerFactory.getLogger(RoomController.class);

    @PostMapping("/vacantRooms")
    public List<Long> getAllMonitoringRoomsWithZeroOccupancy(@RequestBody List<String> roomId_string) {
        List<Long> roomId = new ArrayList<>();
        for (String str : roomId_string) {
            roomId.add(Long.parseLong(str));
        }
        LOG.info("Trying to check Occupancy of rooms");
        return this.occupancyService.getRoomsWithZeroOccupancy(roomId);

    }

}
