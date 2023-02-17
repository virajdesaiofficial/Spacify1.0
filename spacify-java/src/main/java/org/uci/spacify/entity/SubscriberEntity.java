package org.uci.spacify.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="subscriber", schema="corespacify")
public class SubscriberEntity {
    @EmbeddedId
    private UserRoomPK userRoomPK;

    public SubscriberEntity() {
    }
}
