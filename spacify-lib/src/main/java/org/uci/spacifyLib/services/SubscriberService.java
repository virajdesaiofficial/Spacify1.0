package org.uci.spacifyLib.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.SubscriberEntity;
import org.uci.spacifyLib.entity.UserRoomPK;
import org.uci.spacifyLib.repository.SubscriberRepository;

import java.util.ArrayList;
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
    public List<String> getUserIdsByRoomIds(List<Long> roomIds) {
        List<String> userIds = new ArrayList<>();

        for (Long roomId : roomIds) {
            List<SubscriberEntity> subscribers = subscriberRepository.findAllByUserRoomPK_RoomId(roomId);
            for (SubscriberEntity subscriber : subscribers) {
                String userId = subscriber.getUserRoomPK().getUserId();
                if (!userIds.contains(userId)) {
                    userIds.add(userId);
                }
            }
        }

        return userIds;
    }
}