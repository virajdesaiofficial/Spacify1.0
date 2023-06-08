package org.uci.spacifyEngine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.SubscriberEntity;
import org.uci.spacifyLib.entity.UserEntity;
import org.uci.spacifyLib.entity.UserRoomPK;
import org.uci.spacifyLib.repository.SubscriberRepository;
import org.uci.spacifyLib.repository.UserRepository;

import java.util.Optional;

@Service
public class UserSubscriberService {

    private final UserRepository userRepository;
    private final SubscriberRepository subscriberRepository;

    @Autowired
    public UserSubscriberService(UserRepository userRepository, SubscriberRepository subscriberRepository) {
        this.userRepository = userRepository;
        this.subscriberRepository = subscriberRepository;
    }

    public boolean updateSubscriberStatus(String phoneNumber, int roomId, boolean subscribed) {
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepository.findByPhoneNumber(phoneNumber));
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            UserRoomPK userRoomPK = new UserRoomPK(user.getUserId(), (long) roomId);
            Optional<SubscriberEntity> subscriberOptional = subscriberRepository.findByUserRoomPK(userRoomPK);
            if (subscriberOptional.isPresent()) {
                SubscriberEntity subscriber = subscriberOptional.get();
                subscriber.setSubscribed(subscribed);
                subscriberRepository.save(subscriber);
                return true;
            }
        }
        return false;
    }

    public boolean isSubscriberSubscribed(String phoneNumber, Long roomId) {
        Optional<UserEntity> userOptional = Optional.ofNullable(userRepository.findByPhoneNumber(phoneNumber));
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            UserRoomPK userRoomPK = new UserRoomPK(user.getUserId(), (long) roomId);
            Optional<SubscriberEntity> subscriberOptional = subscriberRepository.findByUserRoomPK(userRoomPK);
            if (subscriberOptional.isPresent()) {
                SubscriberEntity subscriber = subscriberOptional.get();
                return subscriber.isSubscribed();
            }
        }
        return false;
    }
}
