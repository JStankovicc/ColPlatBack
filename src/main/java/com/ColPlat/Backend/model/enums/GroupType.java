package com.ColPlat.Backend.model.enums;

public enum GroupType {
    ENTIRE_COMPANY("Entire Company"),
    TEAM("Team"),
    SALES_TEAM("Sales Team"),
    PROJECT_TEAM("Project Team"),
    MANAGEMENT("Management"),
    CUSTOM_GROUP("Custom Group");

    private final String displayName;

    GroupType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
