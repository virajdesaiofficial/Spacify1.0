package org.uci.spacify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacify.entity.User;
import org.uci.spacify.services.UserService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    @Autowired
    private UserService userService;

    /*
    GET API for getting all user
     */
    @GetMapping("/all")
    public List<User> getAllUsers() {
        return this.userService.getAllUsers();
    }

    /*
    GET API for getting a specific user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<User> getUser(@PathVariable(value = "userId") String userId) {
        Optional<User> user = this.userService.getUser(userId);
        if (user.isPresent()) {
            return ResponseEntity.ok().body(user.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*
    POST API for adding new user
     */
    @PostMapping("/addUser")
    public User createUser(@RequestBody User user) {
        return this.userService.addUser(user);
    }

}
