package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacifyLib.dto.CreateRoom;
import org.uci.spacifyLib.dto.UiRules;
import org.uci.spacifyPortal.services.CreateRoomService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/createroom")
public class CreateRoomController {

    @Autowired
    private CreateRoomService createRoomService;

    @PostMapping("/room")
    public ResponseEntity<String> createRoom(@RequestBody CreateRoom request) throws Exception {
        String roomId = request.getRoomId();
        String owner = request.getOwner();
        List<UiRules> rules = request.getRules();

        // Call the create room service with the provided data
        createRoomService.createRoom(roomId, owner, rules);

        return new ResponseEntity<>("Room created successfully", HttpStatus.CREATED);
    }

}
