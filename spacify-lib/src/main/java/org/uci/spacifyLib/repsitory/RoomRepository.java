package org.uci.spacifyLib.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.RoomEntity;

@Repository
public interface RoomRepository  extends JpaRepository<RoomEntity, Long> {
}
