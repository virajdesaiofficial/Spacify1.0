package org.uci.spacifyEngine.calculators;

import org.uci.spacifyLib.dto.Rule;
import org.uci.spacifyLib.entity.ReservationEntity;

public class DurationRuleCalculator implements RuleCalculator {
    private ReservationEntity reservationEntity;

    public DurationRuleCalculator(ReservationEntity reservationEntity) {
        this.reservationEntity = reservationEntity;
    }

    public void calculate(Rule rule) {

    }
}
