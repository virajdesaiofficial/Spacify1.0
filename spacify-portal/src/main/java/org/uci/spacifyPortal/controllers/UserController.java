package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacifyLib.entity.UserEntity;
import org.uci.spacifyPortal.services.OwnerService;
import org.uci.spacifyPortal.services.UserService;

import java.util.List;

@CrossOrigin(maxAge = 3600)
@RestController
@RequestMapping("api/v1/user")
public class UserController {

    @Autowired
    private OwnerService ownerService;

    @Autowired
    private UserService userService;

    @GetMapping("/eligibleOwners")
    public List<UserEntity> getAllEligibleOwners() {
        return this.userService.getAllEligibleOwners();
    }
}
