package com.mediscreen.assessment.model;

public class Trigger {

    public static final Trigger HEMOGLOBIN = new Trigger("Hemoglobin A1C");
    public static final Trigger MICROALBUMIN = new Trigger("Microalbumin");
    public static final Trigger HEIGHT = new Trigger("Height");
    public static final Trigger WEIGHT = new Trigger("Weight");
    public static final Trigger SMOKER = new Trigger("Smoker");
    public static final Trigger ABNORMAL = new Trigger("Abnormal");
    public static final Trigger CHOLESTEROL = new Trigger("Cholesterol");
    public static final Trigger DIZZINESS = new Trigger("Dizziness");
    public static final Trigger RELAPSE = new Trigger("Relapse");
    public static final Trigger REACTION = new Trigger("Reaction");
    public static final Trigger ANTIBODIES = new Trigger("Antibodies");

    private final String trigger;

    private Trigger(String trigger) {
        this.trigger = trigger;
    }

    public String getTrigger() {
        return trigger;
    }
}
