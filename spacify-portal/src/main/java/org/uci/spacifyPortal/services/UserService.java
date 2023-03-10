package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.AccessLevel;
import org.uci.spacifyLib.entity.UserEntity;
import org.uci.spacifyLib.repository.UserRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllEligibleOwners() {
        return this.userRepository.findByAccessLevelIn(Arrays.asList(AccessLevel.ADMIN, AccessLevel.FACULTY));
    }

    public Optional<UserEntity> getUser(String userId) {
        return this.userRepository.findById(userId);
    }
}
