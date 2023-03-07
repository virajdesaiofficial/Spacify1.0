package org.uci.spacifyLib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.RoomEntity;

import java.util.List;

@Repository
public interface RoomRepository  extends JpaRepository<RoomEntity, Long> {
    List<RoomEntity> findByroomType(String roomType);
}
