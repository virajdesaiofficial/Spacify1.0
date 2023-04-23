package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyLib.entity.*;
import org.uci.spacifyPortal.services.*;
import org.uci.spacifyPortal.utilities.MessageResponse;
import org.uci.spacifyPortal.utilities.RegisterUserRequest;
import org.uci.spacifyPortal.utilities.RoomDetail;
import org.uci.spacifyPortal.utilities.UserDetail;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private CreateRoomService roomService;

    @Autowired
    private UserService userService;

    @Autowired
    private IncentiveService incentiveService;

    @Autowired
    private SubscriberService subscriberService;

    @GetMapping("/eligibleOwners")
    public List<UserEntity> getAllEligibleOwners() {
        return this.userService.getAllEligibleOwners();
    }

    private List<RoomDetail> getOwnedRoomDetails(String userId) {
        List<OwnerEntity> ownerEntities = this.ownerService.getAllOwnedRooms(userId);
        List<RoomDetail> ownedRooms = new ArrayList<RoomDetail>();
        if (!ownerEntities.isEmpty()) {
            List<Long> ownerRoomIds = ownerEntities
                    .stream()
                    .map(e -> e.getUserRoomPK().getRoomId())
                    .collect(Collectors.toList());
            ownedRooms = this.roomService.getRoomsBasedOnIds(ownerRoomIds)
                    .stream()
                    .map(room -> new RoomDetail(room.getRoomId(), room.getRoomName()))
                    .collect(Collectors.toList());
        }

        return ownedRooms;
    }

    private List<RoomDetail> getSubscribedRoomDetails(String userId) {
        List<SubscriberEntity> subscriberEntities = this.subscriberService.getAllSubscribedRooms(userId);
        List<RoomDetail> subscribedRooms = new ArrayList<RoomDetail>();
        if (!subscriberEntities.isEmpty()) {
            List<Long> subscribedRoomIds = subscriberEntities
                    .stream()
                    .map(e -> e.getUserRoomPK().getRoomId())
                    .collect(Collectors.toList());
            subscribedRooms = this.roomService.getRoomsBasedOnIds(subscribedRoomIds)
                    .stream()
                    .map(room -> new RoomDetail(room.getRoomId(), room.getRoomName()))
                    .collect(Collectors.toList());
        }
        return subscribedRooms;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDetail> getUserDetails(@PathVariable String userId) {
        UserDetail userDetail = new UserDetail();
        Optional<UserEntity> userEntity = this.userService.getUser(userId);
        if (userEntity.isPresent()) {
            userDetail.setUser(userEntity.get());
            List <IncentiveEntity> incentiveEntities = this.incentiveService.getIncentivesForUser(userId);
            userDetail.setIncentives(incentiveEntities);
            userDetail.setOwnedRooms(getOwnedRoomDetails(userId));
            userDetail.setSubscribedRooms(getSubscribedRoomDetails(userId));
            userDetail.setTotalIncentive(userDetail.getTotalIncentive());
            return new ResponseEntity<>(userDetail, HttpStatus.OK);
        }

        return new ResponseEntity<>(userDetail, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signup")
    public ResponseEntity<MessageResponse> registerNewUser(@RequestBody RegisterUserRequest registerUserRequest) {
        String userId = registerUserRequest.getUserId();
        Optional<UserEntity> userEntity = this.userService.getUser(userId);
        if (userEntity.isPresent()) {
            MessageResponse messageResponse = new MessageResponse("User name already exists, please choose another user name.", false);
            return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
        }
        boolean userCreated = this.userService.createAndSaveNewUser(registerUserRequest.getUserId(),
                registerUserRequest.getEmailId(),
                registerUserRequest.getPassword(),
                registerUserRequest.getFirstName(),
                registerUserRequest.getLastName(),
                registerUserRequest.getMacAddress());

        if (userCreated) {
            MessageResponse messageResponse = new MessageResponse("User successfully registered! Please sign in.", true);
            return new ResponseEntity<>(messageResponse, HttpStatus.OK);
        }

        MessageResponse messageResponse = new MessageResponse("Unable to Register. Please try again later.", false);
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/signin")
    public ResponseEntity<MessageResponse> userSignIn(@RequestBody RegisterUserRequest registerUserRequest) {
        String userId = registerUserRequest.getUserId();
        Optional<AuthenticationEntity> authenticationEntity = this.userService.getAuthentication(userId);
        if (authenticationEntity.isPresent()) {
            boolean signInAllowed = this.userService.verifyUserSignIn(authenticationEntity.get(), registerUserRequest.getPassword());
            if (signInAllowed) {
                MessageResponse messageResponse = new MessageResponse("Successfully signed in!", true);
                return new ResponseEntity<>(messageResponse, HttpStatus.OK);
            } else {
                MessageResponse messageResponse = new MessageResponse("Invalid credentials. Please try again.", false);
                return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
            }
        }

        MessageResponse messageResponse = new MessageResponse("User name does not exist. Please register if you are a new user.", false);
        return new ResponseEntity<>(messageResponse, HttpStatus.BAD_REQUEST);
    }
}
