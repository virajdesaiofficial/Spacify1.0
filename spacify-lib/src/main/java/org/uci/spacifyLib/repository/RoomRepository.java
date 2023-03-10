package org.uci.spacifyLib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.AvailableSlotsEntity;
import org.uci.spacifyLib.entity.RoomEntity;

import java.util.List;

@Repository
public interface RoomRepository  extends JpaRepository<RoomEntity, Long> {

    RoomEntity findByTippersSpaceId(int tippersSpaceId);

    List<RoomEntity> findByRoomIdIn(List<Long> roomIds);

    List<RoomEntity> findByroomType(String room_type);
}
