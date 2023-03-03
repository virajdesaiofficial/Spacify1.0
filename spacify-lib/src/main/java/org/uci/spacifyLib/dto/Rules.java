package org.uci.spacifyLib.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Rules{

    private String ruleId;
    private double incentive;
    private double threshold;
    private double calculatedResult;
    private double creditForRule;

}
