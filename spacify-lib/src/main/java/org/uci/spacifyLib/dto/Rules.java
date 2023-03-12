package org.uci.spacifyLib.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Rules{

    private String ruleId;
    private Long incentive;
    private double thresholdValue;
    private double calculatedValue;
    private double creditForRule;
    private boolean isFired;

}
