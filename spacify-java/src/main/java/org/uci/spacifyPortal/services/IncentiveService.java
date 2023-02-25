package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyPortal.entity.IncentiveEntity;
import org.uci.spacifyPortal.repsitory.IncentiveRepository;

import java.util.List;

@Service
public class IncentiveService {

    @Autowired
    private IncentiveRepository incentiveRepository;

    public List<IncentiveEntity> getAll() {
        return this.incentiveRepository.findAll();
    }
}
