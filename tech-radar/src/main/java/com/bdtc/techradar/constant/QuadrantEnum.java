package com.bdtc.techradar.constant;

//"Languages & Frameworks", "languages-and-frameworks"
//"Platforms & Operations", "platforms-and-operations"
//"Tools", "tools"
//"Methods & Patterns", "methods-and-patterns"

public enum QuadrantEnum {
    FIRST_QUADRANT("first-quadrant"),
    SECOND_QUADRANT("second-quadrant"),
    THIRD_QUADRANT("third-quadrant"),
    FOURTH_QUADRANT("fourth-quadrant");

    private final String quandrantId;

    QuadrantEnum(String quandrantId) {
        this.quandrantId = quandrantId;
    }

    public String getTitle() {
        return quandrantId;
    }
}
