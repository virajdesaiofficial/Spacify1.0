package org.uci.spacifyPortal.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.dto.Rule;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyLib.repsitory.RoomRepository;
import org.uci.spacifyLib.utilities.SpacifyUtility;

import java.util.List;
import java.util.Optional;

@Service
public class RuleService {
    @Autowired
    private RoomRepository roomRepository;

    public boolean addRules(long roomId, String owner, List<Rule> rules) throws JsonProcessingException {
        Optional<RoomEntity> room = this.roomRepository.findById(roomId);

        if (room.isPresent()) {
            room.get().setRoomRules(SpacifyUtility.serializeListOfRules(rules));
            this.roomRepository.save(room.get());
            return true;
        }
        return false;
    }
}
