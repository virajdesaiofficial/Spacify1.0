package org.uci.spacifyLib.dto;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
public class RoomDetail {
    private Long roomId;

    private String roomDescription;

    private String roomType;

    public RoomDetail() {
    }

    public RoomDetail(Long roomId, String roomDescription, String roomType) {
        this.roomId = roomId;
        this.roomDescription = roomDescription;
        this.roomType = roomType;
    }
}
