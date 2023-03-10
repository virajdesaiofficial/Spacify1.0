package org.uci.spacifyLib.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Getter
@Setter
@Embeddable
public class UserRoomPK implements Serializable {
    @Column(name = "user_id")
    private String userId;

    @Column(name = "room_id")
    private Long roomId;

    public UserRoomPK() {
    }

    public UserRoomPK(String userId, Long roomId) {
        this.userId = userId;
        this.roomId = roomId;
    }
}
