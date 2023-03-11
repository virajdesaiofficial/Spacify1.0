package org.uci.spacifyLib.entity;

public enum RoomType {
    STUDY("STUDY"),
    OFFICE("Office"),
    COMMON_SPACE("Common Space"),
    CONFERENCE("Conference");

    private String prettyName;

    RoomType(String prettyName) {
        this.prettyName = prettyName;
    }
}
