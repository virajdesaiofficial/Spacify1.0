package org.uci.spacifyPortal.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyPortal.entity.MacAddressEntity;
import org.uci.spacifyPortal.entity.MacAddressPK;

@Repository
public interface MacAddressRepository extends JpaRepository<MacAddressEntity, MacAddressPK> {
}
