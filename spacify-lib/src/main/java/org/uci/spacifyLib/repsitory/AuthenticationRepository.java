package org.uci.spacifyLib.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.AuthenticationEntity;

@Repository
public interface AuthenticationRepository extends JpaRepository<AuthenticationEntity, String> {

}