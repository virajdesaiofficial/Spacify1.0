package org.uci.spacifyEngine.calculators;

import org.uci.spacifyLib.dto.Rule;
import org.uci.spacifyLib.entity.ReservationEntity;

public class OccupancyRuleCalculator implements RuleCalculator{
    private ReservationEntity reservationEntity;

    public OccupancyRuleCalculator(ReservationEntity reservationEntity) {
        this.reservationEntity = reservationEntity;
    }

    private int calculateActualOccupancy() {
        return 8;
    }

    private double calculateThresholdOccupancy(int expectedOccupancy, Rule rule) {
        return expectedOccupancy * rule.getThresholdValue()/100;
    }

    public void calculate(Rule rule) {
        int expectedOccupancy = this.reservationEntity.getGuests();
        int actualOccupancy = calculateActualOccupancy();
        double thresholdOccupancy = calculateThresholdOccupancy(expectedOccupancy, rule);

        rule.setCalculatedValue(actualOccupancy);
        rule.setThresholdValue(thresholdOccupancy);

    }
}
