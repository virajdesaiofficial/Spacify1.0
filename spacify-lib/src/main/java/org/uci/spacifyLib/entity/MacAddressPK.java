package org.uci.spacifyLib.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class MacAddressPK implements Serializable {
    @Column(name = "user_id")
    private String userId;
    @Column(name = "mac_address")
    private String macAddress;

    public MacAddressPK() {
    }
}
