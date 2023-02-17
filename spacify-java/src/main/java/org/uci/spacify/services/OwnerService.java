package org.uci.spacify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacify.entity.OwnerEntity;
import org.uci.spacify.repsitory.OwnerRepository;

import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public List<OwnerEntity> getAll() {
        return this.ownerRepository.findAll();
    }
}
