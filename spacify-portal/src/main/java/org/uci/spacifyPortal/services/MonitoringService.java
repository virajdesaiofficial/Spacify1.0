package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.MonitoringEntity;
import org.uci.spacifyLib.repsitory.MonitoringRepository;

import java.util.List;

@Service
public class MonitoringService {

    @Autowired
    private MonitoringRepository monitoringRepository;

    public List<MonitoringEntity> getAll() {
        return this.monitoringRepository.findAll();
    }
}
