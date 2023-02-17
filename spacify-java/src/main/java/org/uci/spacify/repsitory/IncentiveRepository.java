package org.uci.spacify.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacify.entity.IncentiveEntity;

@Repository
public interface IncentiveRepository extends JpaRepository<IncentiveEntity, Long> {
}
