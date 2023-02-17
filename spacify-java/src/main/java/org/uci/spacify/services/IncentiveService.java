package org.uci.spacify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacify.entity.IncentiveEntity;
import org.uci.spacify.repsitory.IncentiveRepository;

import java.util.List;

@Service
public class IncentiveService {

    @Autowired
    private IncentiveRepository incentiveRepository;

    public List<IncentiveEntity> getAll() {
        return this.incentiveRepository.findAll();
    }
}
