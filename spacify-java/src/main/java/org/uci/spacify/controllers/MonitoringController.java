package org.uci.spacify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacify.entity.MonitoringEntity;
import org.uci.spacify.services.MonitoringService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/monitoring")
public class MonitoringController {
    @Autowired
    private MonitoringService monitoringService;

    @GetMapping("/all")
    public List<MonitoringEntity> getAll() {
        return this.monitoringService.getAll();
    }
}
