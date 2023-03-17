package org.uci.spacifyEngine.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.IncentiveEntity;
import org.uci.spacifyLib.entity.UserEntity;
import org.uci.spacifyLib.repsitory.IncentiveRepository;
import org.uci.spacifyLib.repsitory.UserRepository;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IncentiveService {
    @Autowired
    private IncentiveRepository incentiveRepository;

    @Autowired
    private UserRepository userRepository;

    public Map<UserEntity, Long> calculateTotalIncentives() {
        List<IncentiveEntity> incentiveEntities = this.incentiveRepository.findAllByAdded(false);
        Set<String> userIds = incentiveEntities.stream().map(IncentiveEntity::getUserId).collect(Collectors.toSet());
        List<UserEntity> userEntities = this.userRepository.findAllById(userIds);

        Map<UserEntity, Long> userIncentiveMap = new HashMap<UserEntity, Long>();
        incentiveEntities.forEach(incentiveEntity -> {
            Optional<UserEntity> userEntity1 = userEntities.stream()
                    .filter(userEntity -> Objects.equals(userEntity.getUserId(), incentiveEntity.getUserId())).findFirst();

            if (userEntity1.isPresent()) {
                UserEntity userEntity = userEntity1.get();
                userIncentiveMap.computeIfAbsent(userEntity, k -> userEntity.getTotalIncentives());
                userIncentiveMap.put(userEntity, userIncentiveMap.get(userEntity) + incentiveEntity.getIncentivePoints());
                incentiveEntity.setAdded(true);
            }
        });

        List<UserEntity> userEntitiesToSave = new ArrayList<UserEntity>();
        for (Map.Entry<UserEntity, Long> entry: userIncentiveMap.entrySet()){
            UserEntity userEntity = entry.getKey();
            userEntity.setTotalIncentives(entry.getValue());
            userEntitiesToSave.add(userEntity);
        }

        this.incentiveRepository.saveAll(incentiveEntities);
        this.userRepository.saveAll(userEntitiesToSave);
        return userIncentiveMap;
    }
}
