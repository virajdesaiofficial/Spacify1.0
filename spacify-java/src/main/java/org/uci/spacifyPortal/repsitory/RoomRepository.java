package org.uci.spacifyPortal.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyPortal.entity.RoomEntity;

@Repository
public interface RoomRepository  extends JpaRepository<RoomEntity, Long> {
}
