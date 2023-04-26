package org.uci.spacifyEngine.calculators;

import org.uci.spacifyEngine.services.MonitoringService;
import org.uci.spacifyLib.dto.Rule;
import org.uci.spacifyLib.entity.ReservationEntity;

public class OccupancyRuleCalculator implements RuleCalculator{
    private final ReservationEntity reservationEntity;

    private final MonitoringService monitoringService;

    public OccupancyRuleCalculator(ReservationEntity reservationEntity, MonitoringService monitoringService) {
        this.reservationEntity = reservationEntity;
        this.monitoringService = monitoringService;
    }

    private int calculateActualOccupancy() {
        return this.monitoringService.getActualRoomOccupancy(reservationEntity);
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
