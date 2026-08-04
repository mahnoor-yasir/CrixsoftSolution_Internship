package com.university.model;

public class Grade {
    private String gradeLetter;
    private double gradePoint;
    private String description;

    public Grade(String gradeLetter, double gradePoint, String description) {
        this.gradeLetter = gradeLetter;
        this.gradePoint = gradePoint;
        this.description = description;
    }

    // Getters and Setters
    public String getGradeLetter() { return gradeLetter; }
    public double getGradePoint() { return gradePoint; }
    public String getDescription() { return description; }
}
