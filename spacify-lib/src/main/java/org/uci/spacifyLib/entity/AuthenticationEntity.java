package org.uci.spacifyLib.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import static org.uci.spacifyLib.utilities.Constants.SCHEMA_NAME;

@Getter
@Setter
@Entity
@Table(name = "authentication", schema = SCHEMA_NAME)
public class AuthenticationEntity {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "hashed_password")
    @NotNull
    private String hashedPassword;

    public AuthenticationEntity() {
    }

    public AuthenticationEntity(String userId, String hashedPassword) {
        this.userId = userId;
        this.hashedPassword = hashedPassword;
    }
}
