package org.uci.spacifyLib.entity;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

import static org.uci.spacifyLib.utilities.Constants.SCHEMA_NAME;

@Getter
@Setter
@Entity
@Table(name = "room", schema = SCHEMA_NAME)
public class RoomEntity {
    @Id
    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "tippers_space_id", unique = true)
    @NotNull
    private Integer tippersSpaceId;

    @Column(name = "room_type")
    @Enumerated(EnumType.STRING)
    private RoomType roomType;

    @Column(name = "room_rules")
    private String roomRules;

    @Column(name="description")
    private String description;

    public RoomEntity() {
    }

    public RoomEntity(Integer tippersSpaceId, RoomType roomType, String roomRules, String description) {
        this.tippersSpaceId = tippersSpaceId;
        this.roomType = roomType;
        this.roomRules = roomRules;
        this.description = description;
    }
}
