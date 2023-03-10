package org.uci.spacifyPortal.utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRequest {
    private int tippersSpaceId;

    private String userId;

    private String roomType;

    private String roomName;

    public CreateRequest() {
    }

    public CreateRequest(int tippersSpaceId, String userId, String roomType, String roomName) {
        this.tippersSpaceId = tippersSpaceId;
        this.userId = userId;
        this.roomType = roomType;
        this.roomName = roomName;
    }
}
