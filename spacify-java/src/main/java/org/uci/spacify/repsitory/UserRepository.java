package org.uci.spacify.repsitory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacify.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
}
