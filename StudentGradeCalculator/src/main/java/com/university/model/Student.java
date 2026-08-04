package com.university.model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private String studentId;
    private String name;
    private String department;
    private int semester;
    private List<Double> grades;
    private List<Subject> subjects;
    private double attendancePercentage;

    // Constructor 1: For basic student with grades
    public Student(String name, String studentId) {
        this.name = name;
        this.studentId = studentId;
        this.grades = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.department = "Unknown";
        this.semester = 1;
        this.attendancePercentage = 0.0;
    }

    // Constructor 2: For university-level student with department and semester
    public Student(String studentId, String name, String department, int semester) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.semester = semester;
        this.grades = new ArrayList<>();
        this.subjects = new ArrayList<>();
        this.attendancePercentage = 0.0;
    }

    // Getters and Setters
    public String getStudentId() { return studentId; }
    public void setStudentId(String studentId) { this.studentId = studentId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public int getSemester() { return semester; }
    public void setSemester(int semester) { this.semester = semester; }

    public List<Double> getGrades() { return grades; }
    public void setGrades(List<Double> grades) { this.grades = grades; }

    public List<Subject> getSubjects() { return subjects; }
    public void setSubjects(List<Subject> subjects) { this.subjects = subjects; }

    public double getAttendancePercentage() { return attendancePercentage; }
    public void setAttendancePercentage(double attendancePercentage) { 
        this.attendancePercentage = attendancePercentage; 
    }

    // Add a grade
    public void addGrade(double grade) {
        if (grade >= 0 && grade <= 100) {
            grades.add(grade);
        } else {
            System.out.println("Invalid grade! Must be between 0 and 100.");
        }
    }

    // Add a subject
    public void addSubject(Subject subject) {
        subjects.add(subject);
    }

    // Calculate total marks from subjects
    public double getTotalMarks() {
        return subjects.stream().mapToDouble(Subject::getMarksObtained).sum();
    }

    // Calculate max marks from subjects
    public double getMaxMarks() {
        return subjects.stream().mapToDouble(Subject::getMaxMarks).sum();
    }

    // Calculate overall percentage from subjects
    public double getOverallPercentage() {
        if (subjects.isEmpty()) return 0.0;
        return (getTotalMarks() / getMaxMarks()) * 100;
    }

    // Calculate GPA on 4.0 scale
    public double getGPA() {
        double percentage = getOverallPercentage();
        if (percentage >= 90) return 4.0;
        else if (percentage >= 80) return 3.5;
        else if (percentage >= 70) return 3.0;
        else if (percentage >= 60) return 2.5;
        else if (percentage >= 50) return 2.0;
        else if (percentage >= 40) return 1.5;
        else return 0.0;
    }

    // Get grade letter
    public String getGrade() {
        double percentage = getOverallPercentage();
        if (percentage >= 90) return "A+ (Outstanding)";
        else if (percentage >= 80) return "A (Excellent)";
        else if (percentage >= 70) return "B+ (Very Good)";
        else if (percentage >= 60) return "B (Good)";
        else if (percentage >= 50) return "C+ (Average)";
        else if (percentage >= 40) return "C (Below Average)";
        else if (percentage >= 33) return "D (Pass)";
        else return "F (Fail)";
    }

    // Check if student passed
    public boolean isPassed() {
        if (getOverallPercentage() < 33) return false;
        for (Subject sub : subjects) {
            if (!sub.isPassed(33)) return false;
        }
        return true;
    }

    // Get status
    public String getStatus() {
        if (!isPassed()) return "FAILED";
        if (getOverallPercentage() >= 80) return "DISTINCTION";
        if (getOverallPercentage() >= 70) return "FIRST DIVISION";
        if (getOverallPercentage() >= 60) return "SECOND DIVISION";
        return "PASSED";
    }

    @Override
    public String toString() {
        return String.format("%-10s %-20s %-15s Semester: %d", 
                            studentId, name, department, semester);
    }
}
