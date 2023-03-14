package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyLib.entity.IncentiveEntity;
import org.uci.spacifyLib.entity.OwnerEntity;
import org.uci.spacifyLib.entity.SubscriberEntity;
import org.uci.spacifyLib.entity.UserEntity;
import org.uci.spacifyPortal.services.*;
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
            // TODO: Discuss and convert this logic in a trigger based system. Also, when user uses points,
            // we should save them in the Incentive table. It should be more like an audit table.
            // Will need to create totalIncentive column in User table.
            // Trigger one is also good, just need to take care not to set incentive in user table directly.
            int totalIncentive = 0;
            for (IncentiveEntity incentiveEntity : incentiveEntities) {
                totalIncentive += incentiveEntity.getIncentivePoints();
            }
            userDetail.setTotalIncentive(totalIncentive);
            return new ResponseEntity<>(userDetail, HttpStatus.OK);
        }

        return new ResponseEntity<>(userDetail, HttpStatus.BAD_REQUEST);
    }
}
