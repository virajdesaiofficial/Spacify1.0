package org.uci.spacifyLib.entity;

import lombok.Getter;

@Getter
public enum RoomType {
    STUDY("Study"),
    OFFICE("Office"),
    COMMON_SPACE("Common Space"),
    CONFERENCE("Conference");

    private String prettyName;

    RoomType(String prettyName) {
        this.prettyName = prettyName;
    }
}
