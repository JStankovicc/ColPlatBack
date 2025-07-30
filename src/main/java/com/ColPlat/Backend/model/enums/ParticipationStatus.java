package com.ColPlat.Backend.model.enums;

public enum ParticipationStatus {
    INVITED("Invited"),
    ACCEPTED("Accepted"),
    DECLINED("Declined"),
    TENTATIVE("Maybe"),
    NO_RESPONSE("No Response");

    private final String displayName;

    ParticipationStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
