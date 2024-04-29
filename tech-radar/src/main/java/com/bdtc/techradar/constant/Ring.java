package com.bdtc.techradar.constant;

public enum Ring {
    HOLD("hold"),
    OBSERVE("observe"),
    TRIAL("trial"),
    ADOPT("adopt");

    private final String title;

    Ring(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
