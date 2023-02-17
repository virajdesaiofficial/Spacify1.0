package org.uci.spacify.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacify.entity.SubscriberEntity;
import org.uci.spacify.entity.UserRoomPK;

@Repository
public interface SubscriberRepository extends JpaRepository<SubscriberEntity, UserRoomPK> {
}
