package org.uci.spacify.dto;


import lombok.Getter;
import lombok.Setter;
import org.uci.spacify.entity.UserRoomPK;

import java.util.List;
@Getter
@Setter
public class CreateRoom {
    private String roomId;
    private String owner;
    private List<Rules> rules;

}

