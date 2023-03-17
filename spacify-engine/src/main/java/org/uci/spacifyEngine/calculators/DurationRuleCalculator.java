package org.uci.spacifyEngine.calculators;

import org.apache.commons.math3.util.Pair;
import org.uci.spacifyEngine.services.MonitoringService;
import org.uci.spacifyLib.dto.Rule;
import org.uci.spacifyLib.entity.ReservationEntity;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

public class DurationRuleCalculator implements RuleCalculator {
    private MonitoringService monitoringService;

    private final ReservationEntity reservationEntity;

    public DurationRuleCalculator(ReservationEntity reservationEntity, MonitoringService monitoringService) {
        this.reservationEntity = reservationEntity;
        this.monitoringService = monitoringService;
    }

    public void calculate(Rule rule) {
        Pair<LocalDateTime, LocalDateTime> actualTimeBounds = this.monitoringService.getActualTimeBounds(reservationEntity);
        if (Objects.isNull(actualTimeBounds))
            return;

        LocalDateTime fromTime = reservationEntity.getTimeFrom();
        LocalDateTime toTime = reservationEntity.getTimeTo();
        LocalDateTime actualFromTime = actualTimeBounds.getKey();
        LocalDateTime actualToTime = actualTimeBounds.getValue();

        // present for the reservation (user entered between the reserved time slot)
        if (actualFromTime.isAfter(fromTime) && actualFromTime.isBefore(toTime)) {
            long expectedNoOfMinutes = fromTime.until(toTime, ChronoUnit.MINUTES);
            long actualNoOfMinutes = actualFromTime.until(actualToTime, ChronoUnit.MINUTES);

            rule.setCalculatedValue(actualNoOfMinutes);
            rule.setThresholdValue(expectedNoOfMinutes*rule.getThresholdValue()/100);
        }
    }
}
