package org.uci.spacifyEngine.calculators;

import lombok.Getter;

import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

@Getter
public enum RuleIdEum {
    OCCUPANCY_RULE("Occupancy Rule", "occupancyRule", "occupancyRuleObj"),
    DURATION_RULE("Duration Rule", "durationRule", "durationRuleObj");

    private String prettyName;
    private String droolName;
    private String droolObjectName;

    RuleIdEum(String prettyName, String droolName, String droolObjectName) {
        this.prettyName = prettyName;
        this.droolName = droolName;
        this.droolObjectName = droolObjectName;
    }

    public static RuleIdEum getRoomIdEnum(String prettyName) {
        Optional<RuleIdEum> enumValue = Arrays.stream(RuleIdEum.values()).filter(e -> Objects.equals(e.getPrettyName(), prettyName)).findFirst();
        return enumValue.orElse(null);
    }
}
