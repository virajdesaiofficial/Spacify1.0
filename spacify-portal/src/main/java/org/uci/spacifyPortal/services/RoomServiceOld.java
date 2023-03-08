//package org.uci.spacifyPortal.services;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.uci.spacifyLib.dto.Rule;
//import org.uci.spacifyLib.entity.RoomEntity;
//import org.uci.spacifyLib.entity.RoomType;
//import org.uci.spacifyLib.repsitory.RoomRepository;
//import org.uci.spacifyLib.utilities.SpacifyUtility;
//import org.uci.spacifyPortal.utilities.CreateRequest;
//
//import java.util.List;
//import java.util.Objects;
//import java.util.Optional;
//
//@Service
//public class RoomServiceOld {
//    @Autowired
//    private RoomRepository roomRepository;
//
//    public RoomEntity createRoom(CreateRequest createRequest) {
//        RoomEntity roomEntity = new RoomEntity();
//        roomEntity.setRoomRules(null);
//        roomEntity.setTippersSpaceId(createRequest.getTippersSpaceId());
//        // we set roomId to -1, as the database trigger: setDefault will generate the next ID and use that for the insert.
//        roomEntity.setRoomId(-1L);
//        roomEntity.setRoomType(RoomType.valueOf(createRequest.getRoomType()));
//        roomEntity.setDescription(createRequest.getRoomDescription());
//
//        this.roomRepository.save(roomEntity);
//
//        // we need to get the roomId of the room we just created, hence the find by tippers space id
//        return this.roomRepository.findByTippersSpaceId(createRequest.getTippersSpaceId());
//    }
//
//    public List<RoomEntity> getAllRooms() {
//        return this.roomRepository.findAll();
//    }
//
//    public boolean doesRoomExist(int tippersSpaceId) {
//        return Objects.nonNull(this.roomRepository.findByTippersSpaceId(tippersSpaceId));
//    }
//
//    public boolean addRules(long roomId, String owner, List<Rule> rules) throws JsonProcessingException {
//        Optional<RoomEntity> room = this.roomRepository.findById(roomId);
//
//        if (room.isPresent()) {
//            room.get().setRoomRules(SpacifyUtility.serializeListOfRules(rules));
//            this.roomRepository.save(room.get());
//            return true;
//        }
//        return false;
//    }
//}
