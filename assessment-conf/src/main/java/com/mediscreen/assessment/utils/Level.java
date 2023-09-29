package com.mediscreen.assessment.utils;

public enum Level {

    EMPTY,
    BORDERLINE,
    IN_DANGER,
    EARLY_ONSET;

    public String getLevelAssessmentMessage() {
        switch (this) {
            case BORDERLINE:
                return "Borderline";
            case IN_DANGER:
                return "In danger";
            case EARLY_ONSET:
                return "Early onset";
            default:
                return "None";
        }
    }

}






