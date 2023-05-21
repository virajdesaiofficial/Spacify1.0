package org.uci.spacifyLib.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MacAdressesOfARoom {


    private int spaceId;
    private String macAddress;
    private String startTime;
    private String endTime;
}
