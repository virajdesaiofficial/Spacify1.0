package org.uci.spacifyLib.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
}
