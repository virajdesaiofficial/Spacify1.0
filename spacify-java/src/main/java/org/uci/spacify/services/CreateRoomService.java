package org.uci.spacify.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacify.dto.Rules;
import org.uci.spacify.entity.OwnerEntity;
import org.uci.spacify.entity.RoomEntity;
import org.uci.spacify.entity.UserRoomPK;
import org.uci.spacify.repsitory.OwnerRepository;
import org.uci.spacify.repsitory.RoomRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class CreateRoomService {

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private RoomRepository roomRepository;

    public void createRoom(String roomId, String owner, List<Rules> rules) throws JsonProcessingException {
        List<org.uci.spacify.dto.Rules> roomRules = new ArrayList<>();
        for (Rules rule : rules) {
            org.uci.spacify.dto.Rules roomRule = new org.uci.spacify.dto.Rules();
            roomRule.setRuleId(rule.getRuleId());
            roomRule.setParams(rule.getParams());
            roomRules.add(roomRule);
        }

        ObjectMapper mapper = new ObjectMapper();
        RoomEntity room = new RoomEntity();
        room.setRoomId(Long.valueOf(roomId));
        room.setRoomRules(mapper.writeValueAsString(roomRules));

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
