package org.uci.spacifyPortal.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacifyLib.entity.MacAddressEntity;
import org.uci.spacifyPortal.services.MacAddressService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/macAddress")
public class MacAddressController {
    @Autowired
    private MacAddressService macAddressService;

    @GetMapping("/all")
    public List<MacAddressEntity> getAll() {
        return this.macAddressService.getAll();
    }
}
