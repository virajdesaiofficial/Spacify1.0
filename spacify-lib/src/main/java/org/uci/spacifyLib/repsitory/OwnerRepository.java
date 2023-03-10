package org.uci.spacifyLib.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.OwnerEntity;
import org.uci.spacifyLib.entity.UserRoomPK;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity, UserRoomPK> {
}
