package org.uci.spacifyPortal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.dto.UiRules;
import org.uci.spacifyLib.entity.OwnerEntity;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyLib.entity.UserRoomPK;
import org.uci.spacifyLib.repsitory.OwnerRepository;
import org.uci.spacifyLib.repsitory.RoomRepository;
import org.uci.spacifyLib.utilities.SpacifyUtility;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateRoomService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RoomRepository roomRepository;

    public void createRoom(String roomId, String owner, List<UiRules> rules) throws JsonProcessingException {

        RoomEntity room = new RoomEntity();
        room.setRoomId(Long.valueOf(roomId));
        room.setRoomRules(SpacifyUtility.serializeListOfRules(rules));

        UserRoomPK userRoomPK = new UserRoomPK();
        userRoomPK.setRoom_id(Long.valueOf(roomId));
        userRoomPK.setUser_id(owner);
        OwnerEntity ownerEntity = new OwnerEntity();
        ownerEntity.setUserRoomPK(userRoomPK);

        List<OwnerEntity> owners = new ArrayList<>();
        owners.add(ownerEntity);

        roomRepository.save(room);
        ownerRepository.saveAll(owners);
    }

}
