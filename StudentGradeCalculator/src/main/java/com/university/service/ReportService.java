package com.university.service;

import com.university.model.Student;
import com.university.model.Subject;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportService {

    public void generateStudentReport(Student student, String reportDir) throws IOException {
        String filename = reportDir + "/" + student.getStudentId() + "_" + student.getName() + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("=".repeat(70));
            writer.println("             UNIVERSITY GRADE REPORT");
            writer.println("=".repeat(70));
            writer.println("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("=".repeat(70));
            writer.println("\nSTUDENT INFORMATION");
            writer.println("-".repeat(70));
            writer.printf("  Student ID   : %s\n", student.getStudentId());
            writer.printf("  Name         : %s\n", student.getName());
            writer.printf("  Department   : %s\n", student.getDepartment());
            writer.printf("  Semester     : %d\n", student.getSemester());
            writer.printf("  Attendance   : %.2f%%\n", student.getAttendancePercentage());
            
            writer.println("\nSUBJECT MARKS");
            writer.println("-".repeat(70));
            writer.printf("%-20s %15s %15s %10s\n", "Subject", "Obtained", "Max Marks", "Percentage");
            writer.println("-".repeat(70));
            
            for (Subject subject : student.getSubjects()) {
                writer.printf("%-20s %15.2f %15.2f %9.2f%%\n",
                        subject.getName(),
                        subject.getMarksObtained(),
                        subject.getMaxMarks(),
                        subject.getPercentage());
            }
            
            writer.println("-".repeat(70));
            writer.printf("%-20s %15.2f %15.2f %9.2f%%\n", 
                    "TOTAL", 
                    student.getTotalMarks(), 
                    student.getMaxMarks(), 
                    student.getOverallPercentage());
            
            writer.println("\nFINAL RESULT");
            writer.println("-".repeat(70));
            writer.printf("  Overall Percentage : %.2f%%\n", student.getOverallPercentage());
            writer.printf("  GPA                : %.2f\n", student.getGPA());
            writer.printf("  Grade              : %s\n", student.getGrade());
            writer.printf("  Status             : %s\n", student.getStatus());
            writer.println("\n" + "=".repeat(70));
            writer.println("                   END OF REPORT");
            writer.println("=".repeat(70));
        }
    }

    public void generateClassReport(List<Student> students, String reportDir) throws IOException {
        String filename = reportDir + "/Class_Report_" + 
                LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".txt";
        
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("=".repeat(80));
            writer.println("                     UNIVERSITY CLASS REPORT");
            writer.println("=".repeat(80));
            writer.println("Generated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            writer.println("=".repeat(80));
            
            writer.println("\nCLASS RANKING (Sorted by Performance)");
            writer.println("-".repeat(80));
            
            List<Student> sorted = students.stream()
                    .sorted((s1, s2) -> Double.compare(s2.getOverallPercentage(), s1.getOverallPercentage()))
                    .toList();
            
            writer.printf("%-4s %-12s %-20s %-15s %10s %8s %10s\n", 
                    "Rank", "ID", "Name", "Department", "Percentage", "GPA", "Status");
            writer.println("-".repeat(80));
            
            int rank = 1;
            for (Student student : sorted) {
                writer.printf("%-4d %-12s %-20s %-15s %9.2f%% %7.2f %10s\n",
                        rank++,
                        student.getStudentId(),
                        student.getName(),
                        student.getDepartment(),
                        student.getOverallPercentage(),
                        student.getGPA(),
                        student.getStatus());
            }
            
            writer.println("\n" + "=".repeat(80));
            writer.println("                   END OF REPORT");
            writer.println("=".repeat(80));
        }
    }
}
