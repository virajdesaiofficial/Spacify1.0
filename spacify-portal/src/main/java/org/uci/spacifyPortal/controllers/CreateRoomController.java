package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyLib.dto.Rule;
import org.uci.spacifyLib.dto.RulesRequest;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyPortal.services.CreateRoomService;
import org.uci.spacifyPortal.services.OwnerService;
import org.uci.spacifyPortal.utilities.CreateRequest;
import org.uci.spacifyPortal.utilities.TipperSpace;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    @Autowired
    private CreateRoomService createRoomService;

    @Autowired
    private OwnerService ownerService;

    @PostMapping("/room")
    public ResponseEntity<String> createRoom(@RequestBody RulesRequest request) throws Exception {
        Long roomId = request.getRoomId();
        String owner = request.getUserId();
        List<Rule> rules = request.getRules();

        // Call the create room service with the provided data
        createRoomService.createRoom(roomId, owner, rules);

        return new ResponseEntity<>("Room created successfully", HttpStatus.CREATED);
    }

    /*
   POST API for adding new room and owner
   */
    @PostMapping("/create")
    public ResponseEntity<String>  createRoom(@RequestBody CreateRequest createRequest) {
        try {
            boolean roomExists = this.createRoomService.doesRoomExist(createRequest.getTippersSpaceId());
            if (roomExists) {
                return new ResponseEntity<>("Room is already owned. Please reach out to owner for access.", HttpStatus.IM_USED);
            }
            RoomEntity roomEntity = this.createRoomService.createRoom(createRequest);
            this.ownerService.createOwner(createRequest.getUserId(), roomEntity.getRoomId());
            return new ResponseEntity<>("Successful", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failure", HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public List<RoomEntity> getAllRooms() {
        return  this.createRoomService.getAllRooms();
    }


    //mock tippers api
    @GetMapping("/tippers/all")
    public List<TipperSpace> getAll() {
        List<TipperSpace> list = new ArrayList<TipperSpace>();
        list.add(new TipperSpace("B501", 344));
        list.add(new TipperSpace("A102", 234));
        list.add(new TipperSpace("E100", 222));
        list.add(new TipperSpace("E200", 345));
        list.add(new TipperSpace("B301", 233));

        return list;
    }

    @PostMapping("/addRules")
    public ResponseEntity<String> addRules(@RequestBody RulesRequest request) throws Exception {
        // Call the rule service with the provided data
        this.createRoomService.addRules(request.getRoomId(), request.getUserId(), request.getRules());

        return new ResponseEntity<>("Room created successfully", HttpStatus.CREATED);
    }


}
