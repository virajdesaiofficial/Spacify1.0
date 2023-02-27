package org.uci.spacifyLib.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.uci.spacifyLib.dto.UiRules;
import org.uci.spacifyLib.utilities.SpacifyUtility;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;

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
    private String roomType;

    @Column(name = "room_rules")
    private String roomRules;

    public RoomEntity() {
    }

    public RoomEntity(Integer tippersSpaceId, String roomType, List<UiRules> roomRules) throws JsonProcessingException {
        this.tippersSpaceId = tippersSpaceId;
        this.roomType = roomType;
        this.roomRules = SpacifyUtility.serializeListOfRules(roomRules);
    }
}