package org.uci.spacifyLib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.SubscriberEntity;
import org.uci.spacifyLib.entity.UserRoomPK;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriberRepository extends JpaRepository<SubscriberEntity, UserRoomPK> {
    public List<SubscriberEntity> findAllByUserRoomPK_UserId(String user_id);
    public Optional<SubscriberEntity> findAllByUserRoomPK(UserRoomPK userRoomPK);
    Optional<SubscriberEntity> findByUserRoomPK(UserRoomPK userRoomPK);

    List<SubscriberEntity> findAllByUserRoomPK_RoomId(Long roomId);
}
