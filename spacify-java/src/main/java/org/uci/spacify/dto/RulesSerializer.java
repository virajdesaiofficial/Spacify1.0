package org.uci.spacify.dto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.uci.spacify.dto.Rules;
public class RulesSerializer {
    public static String serialize(Rules rules) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(rules);
    }
}
