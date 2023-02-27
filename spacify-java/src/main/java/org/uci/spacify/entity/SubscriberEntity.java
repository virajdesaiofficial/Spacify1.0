package org.uci.spacify.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import static org.uci.spacify.utilities.Constants.SCHEMA_NAME;

@Getter
@Setter
@Entity
@Table(name="subscriber", schema=SCHEMA_NAME)
public class SubscriberEntity {
    @EmbeddedId
    private UserRoomPK userRoomPK;

    public SubscriberEntity() {
    }

    public SubscriberEntity(UserRoomPK userRoomPK) {
        this.userRoomPK = userRoomPK;
    }
}
