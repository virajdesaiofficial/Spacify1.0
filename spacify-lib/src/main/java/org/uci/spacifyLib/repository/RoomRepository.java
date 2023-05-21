package org.uci.spacifyLib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.RoomEntity;
import org.uci.spacifyLib.entity.RoomType;

import java.util.List;

@Repository
public interface RoomRepository  extends JpaRepository<RoomEntity, Long> {

    RoomEntity findByTippersSpaceId(int tippersSpaceId);

    List<RoomEntity> findByRoomIdIn(List<Long> roomIds);

    List<RoomEntity> findByroomType(RoomType room_type);

    List<RoomEntity> findByroomTypeAndTippersSpaceIdIn(RoomType roomType, List<Integer> tippersSpaceId);

    RoomEntity findByRoomId(long roomId);
}
