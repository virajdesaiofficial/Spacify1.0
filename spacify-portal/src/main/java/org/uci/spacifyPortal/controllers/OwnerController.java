package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacifyLib.entity.OwnerEntity;
import org.uci.spacifyPortal.services.OwnerService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/owner")
public class OwnerController {
    @Autowired
    private OwnerService ownerService;

    @GetMapping("/all")
    public List<OwnerEntity> getAll() {
        return this.ownerService.getAll();
    }
}
