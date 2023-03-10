package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyLib.dto.CreateRoom;
import org.uci.spacifyLib.dto.MacAdressesOfARoom;
import org.uci.spacifyLib.dto.OccupancyOfaRoom;
import org.uci.spacifyLib.dto.UiRules;
import org.uci.spacifyLib.services.TippersConnectivityService;
import org.uci.spacifyPortal.services.CreateRoomService;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/createroom")
public class CreateRoomController {

    @Autowired
    private CreateRoomService createRoomService;

    @Autowired
    private TippersConnectivityService tippersConnectivityService;

    @PostMapping("/room")
    public ResponseEntity<String> createRoom(@RequestBody CreateRoom request) throws Exception {
        String roomId = request.getRoomId();
        String owner = request.getOwner();
        List<UiRules> rules = request.getRules();

        // Call the create room service with the provided data
        createRoomService.createRoom(roomId, owner, rules);

        return new ResponseEntity<>("Room created successfully", HttpStatus.CREATED);
    }


    // to be deleted
    @GetMapping("/tippers")
    @ResponseBody
    public List<OccupancyOfaRoom> getAllBuildings() {

        return tippersConnectivityService.getOccupancyStatusForSpaceId("1606", "2023-03-06 21:00:51.506", "2023-03-06 21:25:51.506").get();
//        return tippersConnectivityService.getMacAddressesForSpaceId("1606", "2023-03-06 21:00:51.506", "2023-03-06 21:25:51.506").get();
//        return tippersConnectivityService.getListOfBuildings();
//        return tippersConnectivityService.getSpaceIdAndRoomName(1605);

//        return data;
    }
}
