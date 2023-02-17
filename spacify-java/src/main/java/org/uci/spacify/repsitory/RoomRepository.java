package org.uci.spacify.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacify.entity.RoomEntity;

@Repository
public interface RoomRepository  extends JpaRepository<RoomEntity, Long> {
}
