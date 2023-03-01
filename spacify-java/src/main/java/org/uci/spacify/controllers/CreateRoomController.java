package org.uci.spacify.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacify.dto.CreateRoom;
import org.uci.spacify.dto.Rules;
import org.uci.spacify.entity.UserRoomPK;
import org.uci.spacify.services.CreateRoomService;

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
        List<Rules> rules = request.getRules();

        // Call the create room service with the provided data
        createRoomService.createRoom(roomId, owner, rules);

        return new ResponseEntity<>("Room created successfully", HttpStatus.CREATED);
    }

}
