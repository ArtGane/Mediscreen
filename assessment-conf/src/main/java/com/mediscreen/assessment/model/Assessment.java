package com.mediscreen.assessment.model;


public class Assessment {

    private Long patId;
    private int age;
    private String riskLevel;

    public Assessment(Long patId, int age, String riskLevel) {
        this.patId = patId;
        this.age = age;
        this.riskLevel = riskLevel;
    }
}
