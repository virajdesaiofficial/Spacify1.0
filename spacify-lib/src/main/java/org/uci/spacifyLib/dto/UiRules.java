package org.uci.spacifyLib.dto;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class UiRules extends Rules {

    private Map<String, String> params;

    public UiRules(Map<String, String> params) {
        this.params = params;
    }

    public  UiRules(){}
}

