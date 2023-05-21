package org.uci.spacifyLib.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OccupancyOfaRoom {

    private int spaceId;
    private int occupancy;
    private String startTime;
    private String endTime;
}
