package org.uci.spacifyLib.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.uci.spacifyLib.entity.AccessLevel;
import org.uci.spacifyLib.entity.UserEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {
    public List<UserEntity> findByAccessLevelIn(List<AccessLevel> accessLevelList);

    public Optional<UserEntity> findByEmail(String email);
    public Optional<UserEntity> findById(String userId);
    UserEntity findByPhoneNumber(String phoneNumber);

}
