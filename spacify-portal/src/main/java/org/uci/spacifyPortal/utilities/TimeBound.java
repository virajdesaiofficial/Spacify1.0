package org.uci.spacifyPortal.utilities;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class TimeBound {
    private LocalDateTime timeFrom;

    private LocalDateTime timeTo;

    public TimeBound() {
    }

    public TimeBound(LocalDateTime timeFrom, LocalDateTime timeTo) {
        this.timeFrom = timeFrom;
        this.timeTo = timeTo;
    }
}
