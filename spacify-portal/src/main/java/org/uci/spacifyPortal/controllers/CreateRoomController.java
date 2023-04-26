package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
//import org.uci.spacifyLib.dto.CreateRoom;
import org.uci.spacifyLib.dto.*;
//import org.uci.spacifyLib.dto.UiRules;
import org.uci.spacifyLib.services.TippersConnectivityService;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyPortal.services.CreateRoomService;
import org.uci.spacifyPortal.services.OwnerService;
import org.uci.spacifyPortal.utilities.CreateRequest;
import org.uci.spacifyPortal.utilities.MessageResponse;
import org.uci.spacifyPortal.utilities.TipperSpace;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("/api/v1/room")
public class CreateRoomController {

    @Autowired
    private CreateRoomService createRoomService;

    @Autowired
    private TippersConnectivityService tippersConnectivityService;

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


    // only for testing
    @GetMapping("/tippers")
    @ResponseBody
    public void getAllBuildings() {

        tippersConnectivityService.getOccupancyStatusForSpaceId("1606", "2023-03-06 21:00:51.506", "2023-03-06 21:25:51.506");
//        return tippersConnectivityService.getMacAddressesForSpaceId("1606", "2023-03-06 21:00:51.506", "2023-03-06 21:25:51.506").get();
//        return tippersConnectivityService.getListOfBuildings();
//        return tippersConnectivityService.getSpaceIdAndRoomName(1605);

//        return data;
    }
    /*
   POST API for adding new room and owner
   */
    @PostMapping("/create")
    public ResponseEntity<MessageResponse>  createRoom(@RequestBody CreateRequest createRequest) {
        try {
            boolean roomExists = this.createRoomService.doesRoomExist(createRequest.getTippersSpaceId());
            if (roomExists) {
                return new ResponseEntity<>(new MessageResponse("Room is already owned. Please reach out to owner for access.", false), HttpStatus.IM_USED);
            }
            RoomEntity roomEntity = this.createRoomService.createRoom(createRequest);
            this.ownerService.createOwner(createRequest.getUserId(), roomEntity.getRoomId());
            return new ResponseEntity<>(new MessageResponse("Your room was successfully created!", true), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new MessageResponse("Not able to create room. Try again later.", false), HttpStatus.BAD_REQUEST);
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
        this.createRoomService.addRules(request.getRoomId(), request.getUserId(), request.getRules());

        return new ResponseEntity<>("Rules created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/buildings")
    public List<RoomDetail> getBuildings(){

        return tippersConnectivityService.getListOfBuildings();
    }

    @GetMapping("/rooms/{spaceId}")
    public List<RoomDetail> getRoomsFromBuildingSpaceId(@PathVariable String spaceId){

        return tippersConnectivityService.getSpaceIdAndRoomName(Integer.parseInt(spaceId));
    }


}
