package org.uci.spacifyLib.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.SubscriberEntity;
import org.uci.spacifyLib.entity.UserRoomPK;

import java.util.List;

@Repository
public interface SubscriberRepository extends JpaRepository<SubscriberEntity, UserRoomPK> {
    public List<SubscriberEntity> findAllByUserRoomPK_UserId(String user_id);
}
