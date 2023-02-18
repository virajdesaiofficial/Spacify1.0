package org.uci.spacify.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static org.uci.spacify.utilities.Constants.SCHEMA_NAME;

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
