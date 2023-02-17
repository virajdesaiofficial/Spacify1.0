package org.uci.spacify.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class MacAddressPK implements Serializable {
    @ManyToOne
    @JoinColumn(name="user_id")
    private UserEntity userEntity;
    @Column(name="mac_address")
    private String macAddress;

    public MacAddressPK() {
    }
}
