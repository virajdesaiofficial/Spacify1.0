package org.uci.spacifyPortal.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyPortal.entity.IncentiveEntity;

@Repository
public interface IncentiveRepository extends JpaRepository<IncentiveEntity, Long> {

}
