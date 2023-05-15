package org.uci.spacifyLib.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReservationRequest {
    int numberOfGuests;
    String timeFrom;
    String timeTo;
    int reservedRoomId;
    String  reservedBy;
    boolean calculateFlag = false;
}
