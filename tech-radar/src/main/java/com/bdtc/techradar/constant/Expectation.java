package com.bdtc.techradar.constant;

public enum Expectation {
    UNKNOWN("-"),
    ZERO_TWO("0 - 2"),
    TWO_FIVE("2 - 5"),
    FIVE_TEN("5 - 10");

    private String description;

    Expectation(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

}
