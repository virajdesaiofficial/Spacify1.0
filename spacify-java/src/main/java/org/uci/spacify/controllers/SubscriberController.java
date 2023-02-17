package org.uci.spacify.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.uci.spacify.entity.SubscriberEntity;
import org.uci.spacify.services.SubscriberService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/subscriber")
public class SubscriberController {
    @Autowired
    private SubscriberService subscriberService;

    @GetMapping("/all")
    public List<SubscriberEntity> getAll() {
        return this.subscriberService.getAll();
    }
}
