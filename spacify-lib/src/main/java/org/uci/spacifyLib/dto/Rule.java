package org.uci.spacifyLib.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class Rule extends Rules {

    private Map<String, String> params;

    public Rule(Map<String, String> params) {
        this.params = params;
    }

    public Rule(){}
}

