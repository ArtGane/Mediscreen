package com.mediscreen.assessment.utils;

public enum Trigger {

    HEMOGLOBIN("Hemoglobin A1C"),
    MICROALBUMIN("Microalbumin"),
    HEIGHT("Height"),
    WEIGHT("Weight"),
    SMOKER("Smoker"),
    ABNORMAL("Abnormal"),
    CHOLESTEROL("Cholesterol"),
    DIZZINESS("Dizziness"),
    RELAPSE("Relapse"),
    REACTION("Reaction"),
    ANTIBODIES("Antibodies");

    private final String trigger;

    Trigger(String trigger) {
        this.trigger = trigger;
    }

    public String getTrigger() {
        return trigger;
    }
}
