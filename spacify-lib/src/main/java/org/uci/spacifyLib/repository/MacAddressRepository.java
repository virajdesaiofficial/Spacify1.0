package org.uci.spacifyLib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.MacAddressEntity;
import org.uci.spacifyLib.entity.MacAddressPK;

import java.util.List;

@Repository
public interface MacAddressRepository extends JpaRepository<MacAddressEntity, MacAddressPK> {
    List<MacAddressEntity> findAllByMacAddressPK_UserId(String userId);
}
