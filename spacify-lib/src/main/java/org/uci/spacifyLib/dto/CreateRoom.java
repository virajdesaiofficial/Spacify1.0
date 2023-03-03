package org.uci.spacifyLib.dto;


import lombok.Getter;
import lombok.Setter;

import java.util.List;
@Getter
@Setter
public class CreateRoom {

    private String roomId;
    private String owner;
    private List<UiRules> rules;

}

