package org.uci.spacifyLib.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import java.sql.Time;

import static org.uci.spacifyLib.utilities.Constants.SCHEMA_NAME;

@Getter
@Setter
@Entity
@Table(name = "subscriber", schema = SCHEMA_NAME)
public class SubscriberEntity {
    @EmbeddedId
    private UserRoomPK userRoomPK;

    @Column(name = "subscribed")
    private boolean subscribed;

    public SubscriberEntity() {
    }

    public SubscriberEntity(UserRoomPK userRoomPK, boolean subscribed) {
        this.userRoomPK = userRoomPK;
        this.subscribed = subscribed;
    }
}
