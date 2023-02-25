package org.uci.spacifyPortal.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyPortal.entity.SubscriberEntity;
import org.uci.spacifyPortal.entity.UserRoomPK;

@Repository
public interface SubscriberRepository extends JpaRepository<SubscriberEntity, UserRoomPK> {
}
