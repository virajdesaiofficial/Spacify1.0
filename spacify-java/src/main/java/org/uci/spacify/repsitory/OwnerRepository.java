package org.uci.spacify.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacify.entity.OwnerEntity;
import org.uci.spacify.entity.UserRoomPK;

@Repository
public interface OwnerRepository extends JpaRepository<OwnerEntity, UserRoomPK> {
}
