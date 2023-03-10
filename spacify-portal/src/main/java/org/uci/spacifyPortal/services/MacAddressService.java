package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.MacAddressEntity;
import org.uci.spacifyLib.repository.MacAddressRepository;

import java.util.List;

@Service
public class MacAddressService {

    @Autowired
    private MacAddressRepository macAddressRepository;

    public List<MacAddressEntity> getAll() {
        return this.macAddressRepository.findAll();
    }
}
