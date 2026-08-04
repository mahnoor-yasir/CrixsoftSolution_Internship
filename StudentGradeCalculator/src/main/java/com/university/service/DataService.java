package com.university.service;

import com.university.model.Student;
import com.university.model.Subject;
import java.io.*;
import java.util.*;

public class DataService {

    public void saveToCSV(List<Student> students, String filename) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filename))) {
            writer.println("StudentID,Name,Department,Semester,Attendance,Subjects");
            for (Student student : students) {
                StringBuilder subjects = new StringBuilder();
                for (Subject sub : student.getSubjects()) {
                    subjects.append(sub.getName()).append(":")
                           .append(sub.getMarksObtained()).append(":")
                           .append(sub.getMaxMarks()).append(";");
                }
                writer.printf("%s,%s,%s,%d,%.2f,%s\n",
                        student.getStudentId(),
                        student.getName(),
                        student.getDepartment(),
                        student.getSemester(),
                        student.getAttendancePercentage(),
                        subjects.toString());
            }
        }
    }

    public List<Student> loadFromCSV(String filename) throws IOException {
        List<Student> students = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            boolean isHeader = true;
            while ((line = reader.readLine()) != null) {
                if (isHeader) { isHeader = false; continue; }
                String[] parts = line.split(",");
                if (parts.length < 6) continue;
                
                Student student = new Student(parts[0], parts[1], parts[2], Integer.parseInt(parts[3]));
                student.setAttendancePercentage(Double.parseDouble(parts[4]));
                
                String[] subjectData = parts[5].split(";");
                for (String sub : subjectData) {
                    if (sub.isEmpty()) continue;
                    String[] subParts = sub.split(":");
                    if (subParts.length == 3) {
                        student.addSubject(new Subject(subParts[0], 
                                Double.parseDouble(subParts[1]), 
                                Double.parseDouble(subParts[2])));
                    }
                }
                students.add(student);
            }
        }
        return students;
    }
}
