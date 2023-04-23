package org.uci.spacifyPortal.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.uci.spacifyLib.entity.*;
import org.uci.spacifyLib.repsitory.AuthenticationRepository;
import org.uci.spacifyLib.repsitory.MacAddressRepository;
import org.uci.spacifyLib.repsitory.UserRepository;

import javax.transaction.Transactional;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MacAddressRepository macAddressRepository;

    @Autowired
    private AuthenticationRepository authenticationRepository;

    public List<UserEntity> getAllEligibleOwners() {
        return this.userRepository.findByAccessLevelIn(Arrays.asList(AccessLevel.ADMIN, AccessLevel.FACULTY));
    }

    public Optional<UserEntity> getUser(String userId) {
        return this.userRepository.findById(userId);
    }

    public String hashPassword(String password) {
        String hashedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashInBytes = md.digest(password.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();
            for (byte b : hashInBytes) {
                sb.append(String.format("%02x", b));
            }
            hashedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hashedPassword;
    }

    @Transactional(rollbackOn = { SQLException.class })
    protected void saveRecords(UserEntity userEntity, MacAddressEntity macAddressEntity, AuthenticationEntity authenticationEntity) throws SQLException {
        this.userRepository.save(userEntity);
        this.macAddressRepository.save(macAddressEntity);
        this.authenticationRepository.save(authenticationEntity);
    }

    public boolean createAndSaveNewUser(String userId, String emailId, String password, String firstName, String lastName, String macAddress) {
        String hashedPassword = this.hashPassword(password);
        if (Objects.isNull(hashedPassword)) {
            return false;
        }
        UserEntity userEntity = new UserEntity(userId, emailId, firstName, lastName, AccessLevel.STUDENT, 0L);
        MacAddressPK macAddressPK = new MacAddressPK();
        macAddressPK.setMacAddress(macAddress);
        macAddressPK.setUserId(userId);

        try {
            this.saveRecords(userEntity, new MacAddressEntity(macAddressPK), new AuthenticationEntity(userId, hashedPassword));
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public Optional<AuthenticationEntity> getAuthentication(String userId) {
        return this.authenticationRepository.findById(userId);
    }

    public boolean verifyUserSignIn(AuthenticationEntity authenticationEntity, String password) {
        String hashedPassword = this.hashPassword(password);
        if (Objects.isNull(hashedPassword)) {
            return false;
        }
        return Objects.equals(authenticationEntity.getHashedPassword(), hashedPassword);
    }

}
