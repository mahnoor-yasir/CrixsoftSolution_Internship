package com.university.util;

import java.util.LinkedHashMap;
import java.util.Map;

public class GradingScale {
    private Map<Double, String> scale;
    private double passingPercentage;

    public GradingScale() {
        this.scale = new LinkedHashMap<>();
        this.passingPercentage = 33.0;
        setDefaultScale();
    }

    private void setDefaultScale() {
        scale.put(90.0, "A+ (Outstanding)");
        scale.put(80.0, "A (Excellent)");
        scale.put(70.0, "B+ (Very Good)");
        scale.put(60.0, "B (Good)");
        scale.put(50.0, "C+ (Average)");
        scale.put(40.0, "C (Below Average)");
        scale.put(33.0, "D (Pass)");
    }

    public void addGradeBoundary(double percentage, String grade) {
        scale.put(percentage, grade);
    }

    public String getGrade(double percentage) {
        for (Map.Entry<Double, String> entry : scale.entrySet()) {
            if (percentage >= entry.getKey()) {
                return entry.getValue();
            }
        }
        return "F (Fail)";
    }

    public double getPassingPercentage() { return passingPercentage; }
    public void setPassingPercentage(double passingPercentage) {
        this.passingPercentage = passingPercentage;
    }

    public void displayScale() {
        System.out.println("\nGRADING SCALE:");
        for (Map.Entry<Double, String> entry : scale.entrySet()) {
            System.out.printf("  >= %.0f%% : %s\n", entry.getKey(), entry.getValue());
        }
        System.out.printf("  < %.0f%%  : F (Fail)\n", scale.keySet().stream().min(Double::compare).orElse(33.0));
    }
}
