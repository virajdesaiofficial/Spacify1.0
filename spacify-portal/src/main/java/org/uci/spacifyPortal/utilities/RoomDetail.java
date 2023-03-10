package org.uci.spacifyPortal.utilities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonSerialize
public class RoomDetail {
    private Long roomId;

    private String roomDescription;

    public RoomDetail() {
    }

    public RoomDetail(Long roomId, String roomDescription) {
        this.roomId = roomId;
        this.roomDescription = roomDescription;
    }
}
