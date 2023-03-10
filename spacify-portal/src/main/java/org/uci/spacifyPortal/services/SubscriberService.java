package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.SubscriberEntity;
import org.uci.spacifyLib.repsitory.SubscriberRepository;

import java.util.List;

@Service
public class SubscriberService {

    @Autowired
    private SubscriberRepository subscriberRepository;

    public List<SubscriberEntity> getAll() {
        return this.subscriberRepository.findAll();
    }
}
