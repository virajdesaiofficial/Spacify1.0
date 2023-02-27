package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uci.spacifyLib.entity.UserEntity;
import org.uci.spacifyPortal.services.UserService;

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
    public List<UserEntity> getAllUsers() {
        return this.userService.getAllUsers();
    }

    /*
    GET API for getting a specific user
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<UserEntity> getUser(@PathVariable(value = "userId") String userId) {
        Optional<UserEntity> user = this.userService.getUser(userId);
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
    public UserEntity createUser(@RequestBody UserEntity userEntity) {
        return this.userService.addUser(userEntity);
    }

}
