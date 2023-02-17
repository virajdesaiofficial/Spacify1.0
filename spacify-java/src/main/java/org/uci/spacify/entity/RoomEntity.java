package org.uci.spacify.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name="room", schema="corespacify")
public class RoomEntity {
    @Id
    @Column(name="room_id")
    private Long roomId;

    @Column(name="tippers_space_id", unique = true)
    @NotNull
    private Long tippersSpaceId;

    @Column(name="room_type")
    private String roomType;

    @Column(name="room_rules")
    private String roomRules;

    public RoomEntity() {
    }

    public RoomEntity(Long tippersSpaceId, String roomType, String roomRules) {
        this.tippersSpaceId = tippersSpaceId;
        this.roomType = roomType;
        this.roomRules = roomRules;
    }
}
