package org.uci.spacify.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@Setter
public class UserRoomPK implements Serializable {
    @Column(name = "user_id")
    private String user_id;

    @Column(name = "room_id")
    private Long room_id;

    public UserRoomPK() {
    }
}
