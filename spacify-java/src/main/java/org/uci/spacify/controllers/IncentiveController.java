package org.uci.spacify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacify.entity.IncentiveEntity;
import org.uci.spacify.services.IncentiveService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/incentive")
public class IncentiveController {
    @Autowired
    private IncentiveService incentiveService;

    @GetMapping("/all")
    public List<IncentiveEntity> getAll() {
        return this.incentiveService.getAll();
    }
}
