package org.uci.spacifyLib.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static org.uci.spacifyLib.utilities.Constants.SCHEMA_NAME;

@Getter
@Setter
@Entity
@Table(name = "mac_address", schema = SCHEMA_NAME)
public class MacAddressEntity {

    @Column(name = "user_id")
    private String userId;

    @Id
    @Column(name = "mac_address")
    private String macAddress;

    public MacAddressEntity() {
    }

    public MacAddressEntity(String userId, String macAddress) {
        this.macAddress = macAddress;
        this.userId = userId;
    }
}
