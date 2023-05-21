package org.uci.spacifyEngine.calculators;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum RuleIdEnum {
    OCCUPANCY_RULE("Occupancy Rule", "occupancyRule", "occupancyRuleObj"),
    DURATION_RULE("Duration Rule", "durationRule", "durationRuleObj"),
    MAX_DEVICE_RULE("Maximum Devices Rule", "maximumDevicesRule", "maximumDevicesObj"),
    MAX_STAY_RULE("Stay Duration Rule", "stayDurationRule", "stayDurationObj");

    private String prettyName;
    private String droolName;
    private String droolObjectName;

    RuleIdEnum(String prettyName, String droolName, String droolObjectName) {
        this.prettyName = prettyName;
        this.droolName = droolName;
        this.droolObjectName = droolObjectName;
    }

    public static RuleIdEnum getRoomIdEnum(String prettyName) {
        Optional<RuleIdEnum> enumValue = Arrays.stream(RuleIdEnum.values()).filter(e -> Objects.equals(e.getPrettyName(), prettyName)).findFirst();
        return enumValue.orElse(null);
    }
}
