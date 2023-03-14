package org.uci.spacifyPortal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.dto.Rule;
import org.uci.spacifyLib.entity.OwnerEntity;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyLib.entity.RoomType;
import org.uci.spacifyLib.entity.UserRoomPK;
import org.uci.spacifyLib.repsitory.OwnerRepository;
import org.uci.spacifyLib.repsitory.RoomRepository;
import org.uci.spacifyLib.utilities.SpacifyUtility;
import org.uci.spacifyPortal.utilities.CreateRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RoomRepository roomRepository;

    public void createRoom(Long roomId, String owner, List<Rule> rules) throws JsonProcessingException {

        RoomEntity room = new RoomEntity();
        room.setRoomId(Long.valueOf(roomId));
        room.setRoomRules(SpacifyUtility.serializeListOfRules(rules));

        UserRoomPK userRoomPK = new UserRoomPK();
        userRoomPK.setRoomId(Long.valueOf(roomId));
        userRoomPK.setUserId(owner);
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setUserRoomPK(userRoomPK);

        List<OwnerEntity> owners = new ArrayList<>();
        owners.add(ownerEntity);

        roomRepository.save(room);
        ownerRepository.saveAll(owners);
    }

    public RoomEntity createRoom(CreateRequest createRequest) {
        RoomEntity roomEntity = new RoomEntity();
        roomEntity.setRoomRules(null);
        roomEntity.setTippersSpaceId(createRequest.getTippersSpaceId());
        // we set roomId to -1, as the database trigger: setDefault will generate the next ID and use that for the insert.
        roomEntity.setRoomId(-1L);
        roomEntity.setRoomType(RoomType.valueOf(createRequest.getRoomType()));
        roomEntity.setRoomName(createRequest.getRoomName());

        this.roomRepository.save(roomEntity);

        // we need to get the roomId of the room we just created, hence the find by tippers space id
        return this.roomRepository.findByTippersSpaceId(createRequest.getTippersSpaceId());
    }

    public List<RoomEntity> getAllRooms() {
        return this.roomRepository.findAll();
    }

    public boolean doesRoomExist(int tippersSpaceId) {
        return Objects.nonNull(this.roomRepository.findByTippersSpaceId(tippersSpaceId));
    }

    public boolean addRules(Long roomId, String owner, List<Rule> rules) throws JsonProcessingException {
        Optional<RoomEntity> room = this.roomRepository.findById(roomId);
        rules.forEach(rule -> rule.setFired(false));
        if (room.isPresent()) {
            room.get().setRoomRules(SpacifyUtility.serializeListOfRules(rules));
            this.roomRepository.save(room.get());
            return true;
        }
        return false;
    }

    public List<RoomEntity> getRoomsBasedOnIds(List<Long> roomIds) {
        return this.roomRepository.findByRoomIdIn(roomIds);
    }
}
