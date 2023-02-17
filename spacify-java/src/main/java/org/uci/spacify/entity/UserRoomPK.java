package org.uci.spacify.entity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.io.Serializable;

@Embeddable
public class UserRoomPK implements Serializable {
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity userEntity;

    @ManyToOne
    @JoinColumn(name = "room_id")
    private RoomEntity roomEntity;

    public UserRoomPK() {
    }

    public UserRoomPK(UserEntity userEntity, RoomEntity roomEntity) {
        this.userEntity = userEntity;
        this.roomEntity = roomEntity;
    }
}
