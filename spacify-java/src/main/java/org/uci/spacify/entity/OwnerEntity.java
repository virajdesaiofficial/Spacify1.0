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
@Table(name="owner", schema=SCHEMA_NAME)
public class OwnerEntity {
    @EmbeddedId
    private UserRoomPK userRoomPK;

    public OwnerEntity() {
    }

    public OwnerEntity(UserRoomPK userRoomPK) {
        this.userRoomPK = userRoomPK;
    }
}
