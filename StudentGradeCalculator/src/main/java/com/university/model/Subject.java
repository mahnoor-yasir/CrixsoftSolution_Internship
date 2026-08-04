package com.university.model;

public class Subject {
    private String name;
    private double marksObtained;
    private double maxMarks;

    public Subject(String name, double marksObtained, double maxMarks) {
        this.name = name;
        this.marksObtained = marksObtained;
        this.maxMarks = maxMarks;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getMarksObtained() { return marksObtained; }
    public void setMarksObtained(double marksObtained) { this.marksObtained = marksObtained; }

    public double getMaxMarks() { return maxMarks; }
    public void setMaxMarks(double maxMarks) { this.maxMarks = maxMarks; }

    public double getPercentage() { 
        return (marksObtained / maxMarks) * 100; 
    }

    public boolean isPassed(double passingPercentage) {
        return getPercentage() >= passingPercentage;
    }

    @Override
    public String toString() {
        return String.format("%-20s %6.2f / %6.2f (%5.2f%%)", 
                            name, marksObtained, maxMarks, getPercentage());
    }
}
