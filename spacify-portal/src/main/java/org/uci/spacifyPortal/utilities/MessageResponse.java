package org.uci.spacifyPortal.utilities;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
    private String message;

    private boolean success;

    public MessageResponse() {
    }

    public MessageResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
