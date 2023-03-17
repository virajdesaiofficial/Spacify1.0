package org.uci.spacifyEngine.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacifyEngine.services.IncentiveService;
import org.uci.spacifyLib.entity.UserEntity;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/incentive")
public class IncentiveCalculation {
    @Autowired
    private IncentiveService incentiveService;

    @GetMapping("/manualTrigger")
    public Map<UserEntity, Long> calculateIncentives() {
        return this.incentiveService.calculateTotalIncentives();
    }
}
