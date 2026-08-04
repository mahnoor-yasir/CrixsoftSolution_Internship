package com.university.service;

import com.university.model.Student;
import java.util.List;

public class GradeService {

    // Calculate average grade from grades list
    public double calculateAverage(Student student) {
        List<Double> grades = student.getGrades();
        if (grades.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.size();
    }

    // Display individual grades
    public void displayGrades(Student student) {
        System.out.println("Grades for " + student.getName() + " (ID: " + student.getStudentId() + "):");
        List<Double> grades = student.getGrades();
        if (grades.isEmpty()) {
            System.out.println("  No grades recorded yet.");
            return;
        }
        for (int i = 0; i < grades.size(); i++) {
            System.out.println("  Grade " + (i + 1) + ": " + grades.get(i));
        }
        System.out.println("  Average: " + String.format("%.2f", calculateAverage(student)));
    }

    // Determine pass/fail status
    public String getPassFailStatus(Student student) {
        double avg = calculateAverage(student);
        if (avg >= 60) {
            return "PASSED (Average: " + String.format("%.2f", avg) + ")";
        } else {
            return "FAILED (Average: " + String.format("%.2f", avg) + ")";
        }
    }

    // Calculate class average
    public double calculateClassAverage(List<Student> students) {
        if (students.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        int totalGrades = 0;
        for (Student student : students) {
            List<Double> grades = student.getGrades();
            totalGrades += grades.size();
            for (double grade : grades) {
                total += grade;
            }
        }
        if (totalGrades == 0) return 0.0;
        return total / totalGrades;
    }
}
