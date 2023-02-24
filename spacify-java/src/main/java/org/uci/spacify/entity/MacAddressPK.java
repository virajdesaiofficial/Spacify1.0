package org.uci.spacify.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class MacAddressPK implements Serializable {
    @Column(name="user_id")
    private String userId;
    @Column(name="mac_address")
    private String macAddress;

    public MacAddressPK() {
    }
}
