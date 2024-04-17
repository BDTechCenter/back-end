package com.bdtc.techradar.constant;

public enum QuadrantEnum {
    LANGUAGES_AND_FRAMEWORKS("Languages & Frameworks", "languages-and-frameworks"),
    PLATFORMS_AND_OPERATIONS("Platforms & Operations", "platforms-and-operations"),
    TOOLS("Tools", "tools"),
    METHODS_AND_PATTERNS("Methods & Patterns", "methods-and-patterns");

    private String title;
    private String name;

    QuadrantEnum(String title, String name) {
        this.title = title;
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public String getName() {
        return name;
    }
}
