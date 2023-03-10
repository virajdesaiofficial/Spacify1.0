package org.uci.spacifyLib.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.MacAddressEntity;
import org.uci.spacifyLib.entity.MacAddressPK;

@Repository
public interface MacAddressRepository extends JpaRepository<MacAddressEntity, MacAddressPK> {
}
