package org.uci.spacify.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacify.entity.MacAddressEntity;
import org.uci.spacify.entity.MacAddressPK;

@Repository
public interface MacAddressRepository extends JpaRepository<MacAddressEntity, MacAddressPK> {
}
