package com.bdtc.techradar.constant;

public enum Flag {
    CHANGED("changed"),
    DEFAULT("default"),
    NEW("new");

    private String title;

    Flag(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
