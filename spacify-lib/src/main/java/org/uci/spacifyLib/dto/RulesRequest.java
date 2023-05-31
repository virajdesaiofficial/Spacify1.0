package org.uci.spacifyLib.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RulesRequest {

    private Long roomId;
    private String userId;

    private String roomName;

    private List<Rule> rules;

    public RulesRequest() {
    }

    public RulesRequest(Long roomId, String roomName) {
        this.roomId = roomId;
        this.roomName = roomName;
    }
}

