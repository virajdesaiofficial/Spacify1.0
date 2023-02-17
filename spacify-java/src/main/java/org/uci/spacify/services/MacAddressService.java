package org.uci.spacify.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacify.entity.MacAddressEntity;
import org.uci.spacify.repsitory.MacAddressRepository;

import java.util.List;

@Service
public class MacAddressService {

    @Autowired
    private MacAddressRepository macAddressRepository;

    public List<MacAddressEntity> getAll() {
        return this.macAddressRepository.findAll();
    }
}
