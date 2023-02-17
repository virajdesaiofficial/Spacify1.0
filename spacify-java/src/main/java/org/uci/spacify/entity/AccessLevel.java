package org.uci.spacify.entity;

public enum AccessLevel {
    ADMIN("admin"),
    STUDENT("student"),
    FACULTY("faculty");

    private String prettyName;

    AccessLevel(String prettyName) {
        this.prettyName = prettyName;
    }
}
