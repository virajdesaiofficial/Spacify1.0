package org.uci.spacify.dto;
import java.util.Map;
import javax.persistence.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Rules {
    private String ruleId;
    private Map<String, String> params;
    public Rules(String ruleId, Map<String, String> params) {
        this.ruleId = ruleId;
        this.params = params;
    }

    public Rules() {
    }

    public String getRuleId() {
        return ruleId;
    }

    public void setRuleId(String ruleId) {
        this.ruleId = ruleId;
    }

    public Map<String, String> getParams() {
        return params;
    }

    public void setParams(Map<String, String> params) {
        this.params = params;
    }


}

