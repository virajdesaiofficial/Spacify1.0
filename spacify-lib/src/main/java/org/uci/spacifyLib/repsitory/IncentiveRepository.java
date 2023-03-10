package org.uci.spacifyLib.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.IncentiveEntity;

@Repository
public interface IncentiveRepository extends JpaRepository<IncentiveEntity, Long> {

}
