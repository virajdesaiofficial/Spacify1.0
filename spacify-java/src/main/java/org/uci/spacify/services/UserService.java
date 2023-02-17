package org.uci.spacify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacify.entity.User;
import org.uci.spacify.repsitory.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers() {
        return this.userRepository.findAll();
    }

    public Optional<User> getUser(String userId) {
        return this.userRepository.findById(userId);
    }

    public User addUser(User user) {
        return this.userRepository.save(user);
    }
}
