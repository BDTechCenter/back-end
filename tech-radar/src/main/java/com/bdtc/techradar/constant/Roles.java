package com.bdtc.techradar.constant;

public enum Roles {
    ADMIN("admin"),
    BDUSER("bduser");

    private final String value;

    Roles(String value) {
        this.value = value;
    }

    public String getRoleValue() {
        return value;
    }
}
