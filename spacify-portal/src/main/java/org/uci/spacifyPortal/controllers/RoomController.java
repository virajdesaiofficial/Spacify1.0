package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyLib.dto.Rule;
import org.uci.spacifyLib.dto.RulesRequest;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyPortal.services.RoomService;
import org.uci.spacifyPortal.services.OwnerService;
import org.uci.spacifyPortal.utilities.CreateRequest;
import org.uci.spacifyPortal.utilities.MessageResponse;
import org.uci.spacifyPortal.utilities.TipperSpace;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/room")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @Autowired
    private OwnerService ownerService;

    @PostMapping("/room")
    public ResponseEntity<String> createRoom(@RequestBody RulesRequest request) throws Exception {
        Long roomId = request.getRoomId();
        String owner = request.getUserId();
        List<Rule> rules = request.getRules();

        // Call the create room service with the provided data
        roomService.createRoom(roomId, owner, rules);

        return new ResponseEntity<>("Room created successfully", HttpStatus.CREATED);
    }

    /*
   POST API for adding new room and owner
   */
    @PostMapping("/create")
    public ResponseEntity<MessageResponse>  createRoom(@RequestBody CreateRequest createRequest) {
        try {
            boolean roomExists = this.roomService.doesRoomExist(createRequest.getTippersSpaceId());
            if (roomExists) {
                return new ResponseEntity<>(new MessageResponse("Room is already owned. Please reach out to owner for access.", false), HttpStatus.IM_USED);
            }
            RoomEntity roomEntity = this.roomService.createRoom(createRequest);
            this.ownerService.createOwner(createRequest.getUserId(), roomEntity.getRoomId());
            return new ResponseEntity<>(new MessageResponse("Your room was successfully created!", true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Not able to create room. Try again later.", false), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/all")
    public List<RoomEntity> getAllRooms() {
        return  this.roomService.getAllRooms();
    }


    //mock tippers api
    @GetMapping("/tippers/all")
    public List<TipperSpace> getAll() {
        List<TipperSpace> list = new ArrayList<TipperSpace>();
        list.add(new TipperSpace("B501", 400));
        list.add(new TipperSpace("A102", 49));
        list.add(new TipperSpace("E100", 46));
        list.add(new TipperSpace("E200", 44));
        list.add(new TipperSpace("B301", 43));

        return list;
    }

    @PostMapping("/addRules")
    public ResponseEntity<String> addRules(@RequestBody RulesRequest request) throws Exception {
        // Call the rule service with the provided data
        this.roomService.addRules(request.getRoomId(), request.getUserId(), request.getRules());

        return new ResponseEntity<>("Rules added successfully", HttpStatus.CREATED);
    }
}
