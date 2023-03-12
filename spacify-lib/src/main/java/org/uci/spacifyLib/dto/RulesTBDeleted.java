package org.uci.spacifyLib.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RulesTBDeleted {

    private int calculatedValue1;
    private int thresholdValue1;
    private int calculatedValue2;
    private int thresholdValue2;
    private int totalCredits = 0;
    private int incentive;
}
