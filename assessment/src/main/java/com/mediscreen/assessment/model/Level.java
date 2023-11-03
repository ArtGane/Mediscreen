package com.mediscreen.assessment.model;

public class Level {

    public static final Level EMPTY = new Level("Empty", "None");
    public static final Level BORDERLINE = new Level("Borderline", "Borderline");
    public static final Level IN_DANGER = new Level("In danger", "In danger");
    public static final Level EARLY_ONSET = new Level("Early onset", "Early onset");

    private String displayName;
    private String assessmentMessage;

    private Level(String displayName, String assessmentMessage) {
        this.displayName = displayName;
        this.assessmentMessage = assessmentMessage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLevelAssessmentMessage() {
        return assessmentMessage;
    }
}
