package org.uci.spacifyLib.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import static org.uci.spacifyLib.utilities.Constants.SCHEMA_NAME;

@Getter
@Setter
@Entity
@Table(name = "mac_address", schema = SCHEMA_NAME)
public class MacAddressEntity {
    @EmbeddedId
    private MacAddressPK macAddressPK;

    public MacAddressEntity() {
    }

    public MacAddressEntity(MacAddressPK macAddressPK) {
        this.macAddressPK = macAddressPK;
    }
}
