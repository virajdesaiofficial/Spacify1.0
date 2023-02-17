package org.uci.spacify.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="owner", schema="corespacify")
public class OwnerEntity {
    @EmbeddedId
    private UserRoomPK userRoomPK;

    public OwnerEntity() {
    }
}
