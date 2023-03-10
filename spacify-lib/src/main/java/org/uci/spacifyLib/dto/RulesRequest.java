package org.uci.spacifyLib.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class RulesRequest {

    private Long roomId;
    private String userId;
    private List<Rule> rules;

}

