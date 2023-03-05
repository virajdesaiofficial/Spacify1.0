package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyLib.dto.RulesRequest;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyPortal.services.OwnerService;
import org.uci.spacifyPortal.services.RoomService;
import org.uci.spacifyPortal.utilities.CreateRequest;

import java.util.Arrays;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private OwnerService ownerService;

    /*
    POST API for adding new room and owner
    */
    @PostMapping("/create")
    public ResponseEntity<String>  createRoom(@RequestBody CreateRequest createRequest) {
        try {
            boolean roomExists = this.roomService.doesRoomExist(createRequest.getTippersSpaceId());
            if (roomExists) {
                return new ResponseEntity<>("Room is already owned. Please reach out to owner for access.", HttpStatus.IM_USED);
            }
            RoomEntity roomEntity = this.roomService.createRoom(createRequest);
            this.ownerService.createOwner(createRequest.getUserId(), roomEntity.getRoomId());
            return new ResponseEntity<>("Successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failure", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public List<RoomEntity> getAllRooms() {
        return  this.roomService.getAllRooms();
    }


    //mock tippers api
    @GetMapping("/tippers/all")
    public List<Integer> getAll() {
        return Arrays.asList(112,113,111,100);
    }

    @PostMapping("/addRules")
    public ResponseEntity<String> addRules(@RequestBody RulesRequest request) throws Exception {
        // Call the rule service with the provided data
        this.roomService.addRules(request.getRoomId(), request.getUserId(), request.getRules());

        return new ResponseEntity<>("Room created successfully", HttpStatus.CREATED);
    }
}
