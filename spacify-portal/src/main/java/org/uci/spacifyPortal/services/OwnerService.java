package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.OwnerEntity;
import org.uci.spacifyLib.repository.OwnerRepository;

import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public List<OwnerEntity> getAll() {
        return this.ownerRepository.findAll();
    }
}
