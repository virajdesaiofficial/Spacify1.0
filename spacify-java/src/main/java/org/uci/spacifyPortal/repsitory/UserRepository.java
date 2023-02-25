package org.uci.spacifyPortal.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyPortal.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
