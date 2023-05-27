package org.uci.spacifyPortal.utilities;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Getter;
import lombok.Setter;
import org.uci.spacifyLib.dto.RoomDetail;
import org.uci.spacifyLib.entity.IncentiveEntity;
import org.uci.spacifyLib.entity.UserEntity;

import java.util.List;

@Getter
@Setter
@JsonSerialize
public class UserDetail {
    private UserEntity user;

    private List<RoomDetail> ownedRooms;

    private List<IncentiveEntity> incentives;

    private Integer totalIncentive;

    private List<RoomDetail> subscribedRooms;

    private List<String> macAddresses;

    public UserDetail() {
    }

    public UserDetail(UserEntity user, List<RoomDetail> ownedRooms, List<IncentiveEntity> incentives, Integer totalIncentive, List<RoomDetail> subscribedRooms, List<String> macAddresses) {
        this.user = user;
        this.ownedRooms = ownedRooms;
        this.incentives = incentives;
        this.totalIncentive = totalIncentive;
        this.subscribedRooms = subscribedRooms;
        this.macAddresses = macAddresses;
    }
}
