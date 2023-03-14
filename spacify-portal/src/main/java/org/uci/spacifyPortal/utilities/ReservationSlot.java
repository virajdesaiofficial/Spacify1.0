package org.uci.spacifyPortal.utilities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class ReservationSlot {
    private Long roomId;

    private List<TimeBound> timeBound;

    public ReservationSlot() {
    }

    public ReservationSlot(Long roomId, List<TimeBound> timeBound) {
        this.roomId = roomId;
        this.timeBound = timeBound;
    }
}
