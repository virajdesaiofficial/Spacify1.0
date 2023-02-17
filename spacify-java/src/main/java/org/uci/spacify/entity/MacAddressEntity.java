package org.uci.spacify.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "mac_address", schema = "corespacify")
public class MacAddressEntity {
    @EmbeddedId
    private MacAddressPK macAddressPK;

    public MacAddressEntity() {
    }

    public MacAddressEntity(MacAddressPK macAddressPK) {
        this.macAddressPK = macAddressPK;
    }
}
