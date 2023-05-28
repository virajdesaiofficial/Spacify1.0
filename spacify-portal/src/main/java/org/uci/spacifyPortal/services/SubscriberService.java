package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.SubscriberEntity;
import org.uci.spacifyLib.entity.UserRoomPK;
import org.uci.spacifyLib.repository.SubscriberRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubscriberService {
    @Autowired
    private SubscriberRepository subscriberRepository;

    public List<SubscriberEntity> getAllSubscribedRooms(String userId) {
        return this.subscriberRepository.findAllByUserRoomPK_UserId(userId);
    }

    public boolean updateUserSubscribedStatus(String userId, String roomId) {
        UserRoomPK pk = new UserRoomPK(userId, Long.parseLong(roomId));
        Optional<SubscriberEntity> subscribe = subscriberRepository.findAllByUserRoomPK(pk);
        subscribe.ifPresent(SubscriberEntity -> {
            SubscriberEntity.setSubscribed(false);
            subscriberRepository.save(subscribe.get());
        });
        return true;
    }
}