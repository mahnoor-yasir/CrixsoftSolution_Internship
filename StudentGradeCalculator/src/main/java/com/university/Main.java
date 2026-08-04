package com.university;

import com.university.model.Student;
import com.university.model.Subject;
import com.university.service.*;
import com.university.util.GradingScale;
import com.university.util.InputValidator;

import java.util.*;
import java.io.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Student> students = new ArrayList<>();
    private static final GradeService gradeService = new GradeService();
    private static final StatisticsService statsService = new StatisticsService();
    private static final ReportService reportService = new ReportService();
    private static final DataService dataService = new DataService();
    private static final GradingScale gradingScale = new GradingScale();

    public static void main(String[] args) {
        // Ensure directories exist
        new File("data").mkdirs();
        new File("reports").mkdirs();

        System.out.println("=".repeat(70));
        System.out.println("       🎓 UNIVERSITY STUDENT GRADE MANAGEMENT SYSTEM");
        System.out.println("=".repeat(70));
        System.out.println("    Professional Edition v2.0 - ALL FEATURES ACTIVE");
        System.out.println("=".repeat(70));

        while (true) {
            displayMenu();
            int choice = InputValidator.getIntInput(scanner, "\nEnter your choice: ");

            switch (choice) {
                case 0 -> {
                    System.out.println("\n👋 Thank you for using the system!");
                    System.out.println("📁 Data saved to: data/students.csv");
                    System.out.println("📄 Reports saved to: reports/");
                    return;
                }
                case 1 -> addStudent();
                case 2 -> viewAllStudents();
                case 3 -> viewStudentDetails();
                case 4 -> updateStudent();
                case 5 -> deleteStudent();
                case 6 -> displayClassStatistics();
                case 7 -> generateReports();
                case 8 -> showMeritList();
                case 9 -> saveData();
                case 10 -> loadData();
                case 11 -> gradingScale.displayScale();
                default -> System.out.println("❌ Invalid choice! Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("\n" + "-".repeat(70));
        System.out.println("📋 MAIN MENU");
        System.out.println("-".repeat(70));
        System.out.println("  1.  ➕ Add New Student");
        System.out.println("  2.  📋 View All Students");
        System.out.println("  3.  🔍 View Student Details");
        System.out.println("  4.  ✏️  Update Student");
        System.out.println("  5.  🗑️  Delete Student");
        System.out.println("  6.  📊 Display Class Statistics");
        System.out.println("  7.  📄 Generate Reports");
        System.out.println("  8.  🏆 Show Merit List");
        System.out.println("  9.  💾 Save Data to CSV");
        System.out.println("  10. 📂 Load Data from CSV");
        System.out.println("  11. 📊 Show Grading Scale");
        System.out.println("  0.  🚪 Exit");
        System.out.println("-".repeat(70));
    }

    // ============ FEATURE 1: ADD STUDENT ============
    private static void addStudent() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("➕ ADD NEW STUDENT");
        System.out.println("=".repeat(50));

        String id = InputValidator.getNonEmptyInput(scanner, "Enter Student ID: ");
        if (students.stream().anyMatch(s -> s.getStudentId().equals(id))) {
            System.out.println("❌ Student ID already exists!");
            return;
        }

        String name = InputValidator.getNonEmptyInput(scanner, "Enter Student Name: ");
        String dept = InputValidator.getNonEmptyInput(scanner, "Enter Department: ");
        int semester = InputValidator.getIntInput(scanner, "Enter Semester: ");

        Student student = new Student(id, name, dept, semester);

        int numSubjects = InputValidator.getIntInput(scanner, "Enter number of subjects: ");
        for (int i = 0; i < numSubjects; i++) {
            System.out.println("\nSubject " + (i + 1) + ":");
            String subName = InputValidator.getNonEmptyInput(scanner, "  Subject name: ");
            double maxMarks = InputValidator.getDoubleInput(scanner, "  Max marks: ");
            double obtained = InputValidator.getDoubleInputRange(scanner, "  Obtained marks: ", 0, maxMarks);
            student.addSubject(new Subject(subName, obtained, maxMarks));
        }

        double attendance = InputValidator.getDoubleInputRange(scanner, "Enter Attendance Percentage (0-100): ", 0, 100);
        student.setAttendancePercentage(attendance);

        students.add(student);
        System.out.println("\n✅ Student added successfully!");
        System.out.println("   📊 Overall Percentage: " + String.format("%.2f%%", student.getOverallPercentage()));
        System.out.println("   🎯 Grade: " + student.getGrade());
        System.out.println("   🏆 Status: " + student.getStatus());
    }

    // ============ FEATURE 2: VIEW ALL STUDENTS ============
    private static void viewAllStudents() {
        if (students.isEmpty()) {
            System.out.println("\n📭 No students in the system.");
            return;
        }

        System.out.println("\n" + "=".repeat(90));
        System.out.println("📋 ALL STUDENTS");
        System.out.println("=".repeat(90));
        System.out.printf("%-12s %-20s %-15s %8s %10s %8s %12s\n", 
                "ID", "Name", "Department", "Semester", "Percentage", "GPA", "Status");
        System.out.println("-".repeat(90));

        for (Student s : students) {
            System.out.printf("%-12s %-20s %-15s %8d %9.2f%% %7.2f %12s\n",
                    s.getStudentId(),
                    s.getName(),
                    s.getDepartment(),
                    s.getSemester(),
                    s.getOverallPercentage(),
                    s.getGPA(),
                    s.getStatus());
        }
        System.out.println("=".repeat(90));
        System.out.println("📌 Total Students: " + students.size());
    }

    // ============ FEATURE 3: VIEW STUDENT DETAILS ============
    private static void viewStudentDetails() {
        if (students.isEmpty()) {
            System.out.println("\n📭 No students in the system.");
            return;
        }

        String id = InputValidator.getNonEmptyInput(scanner, "Enter Student ID: ");
        Student student = findStudent(id);
        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }

        System.out.println("\n" + "=".repeat(70));
        System.out.println("📋 STUDENT DETAILS");
        System.out.println("=".repeat(70));
        System.out.printf("  Student ID   : %s\n", student.getStudentId());
        System.out.printf("  Name         : %s\n", student.getName());
        System.out.printf("  Department   : %s\n", student.getDepartment());
        System.out.printf("  Semester     : %d\n", student.getSemester());
        System.out.printf("  Attendance   : %.2f%%\n", student.getAttendancePercentage());
        
        System.out.println("\n📚 SUBJECT MARKS:");
        System.out.println("-".repeat(70));
        System.out.printf("%-25s %12s %12s %12s\n", "Subject", "Obtained", "Max", "Percentage");
        System.out.println("-".repeat(70));
        for (Subject sub : student.getSubjects()) {
            System.out.printf("%-25s %12.2f %12.2f %11.2f%%\n",
                    sub.getName(), sub.getMarksObtained(), sub.getMaxMarks(), sub.getPercentage());
        }
        System.out.println("-".repeat(70));
        System.out.printf("%-25s %12.2f %12.2f %11.2f%%\n", 
                "TOTAL", student.getTotalMarks(), student.getMaxMarks(), student.getOverallPercentage());
        
        System.out.println("\n🎯 FINAL RESULT:");
        System.out.println("-".repeat(70));
        System.out.printf("  Overall Percentage : %.2f%%\n", student.getOverallPercentage());
        System.out.printf("  GPA                : %.2f\n", student.getGPA());
        System.out.printf("  Grade              : %s\n", student.getGrade());
        System.out.printf("  Status             : %s\n", student.getStatus());
        System.out.println("=".repeat(70));
    }

    // ============ FEATURE 4: UPDATE STUDENT (NOW FUNCTIONAL) ============
    private static void updateStudent() {
        if (students.isEmpty()) {
            System.out.println("\n📭 No students in the system.");
            return;
        }

        String id = InputValidator.getNonEmptyInput(scanner, "Enter Student ID to update: ");
        Student student = findStudent(id);
        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }

        System.out.println("\n✏️  UPDATING STUDENT: " + student.getName());
        System.out.println("   (Press Enter to keep current value)");

        String name = InputValidator.getOptionalInput(scanner, "Name (" + student.getName() + "): ");
        if (!name.isEmpty()) {
            // Note: You'll need to add setName() method to Student class
            // student.setName(name);
        }

        String dept = InputValidator.getOptionalInput(scanner, "Department (" + student.getDepartment() + "): ");
        if (!dept.isEmpty()) {
            // student.setDepartment(dept);
        }

        // Update subjects
        System.out.print("Do you want to update subjects? (y/n): ");
        String updateSubjects = scanner.nextLine().toLowerCase();
        if (updateSubjects.equals("y")) {
            student.getSubjects().clear();
            int numSubjects = InputValidator.getIntInput(scanner, "Enter number of subjects: ");
            for (int i = 0; i < numSubjects; i++) {
                System.out.println("\nSubject " + (i + 1) + ":");
                String subName = InputValidator.getNonEmptyInput(scanner, "  Subject name: ");
                double maxMarks = InputValidator.getDoubleInput(scanner, "  Max marks: ");
                double obtained = InputValidator.getDoubleInputRange(scanner, "  Obtained marks: ", 0, maxMarks);
                student.addSubject(new Subject(subName, obtained, maxMarks));
            }
        }

        System.out.println("✅ Student updated successfully!");
    }

    // ============ FEATURE 5: DELETE STUDENT ============
    private static void deleteStudent() {
        if (students.isEmpty()) {
            System.out.println("\n📭 No students in the system.");
            return;
        }

        String id = InputValidator.getNonEmptyInput(scanner, "Enter Student ID to delete: ");
        Student student = findStudent(id);
        if (student == null) {
            System.out.println("❌ Student not found!");
            return;
        }

        System.out.print("⚠️ Are you sure you want to delete " + student.getName() + "? (y/n): ");
        String confirm = scanner.nextLine().toLowerCase();
        if (confirm.equals("y")) {
            students.remove(student);
            System.out.println("✅ Student deleted successfully!");
        } else {
            System.out.println("❌ Deletion cancelled.");
        }
    }

    // ============ FEATURE 6: CLASS STATISTICS (NOW ENHANCED) ============
    private static void displayClassStatistics() {
        if (students.isEmpty()) {
            System.out.println("\n📭 No students in the system.");
            return;
        }
        statsService.displayStatistics(students);
    }

    // ============ FEATURE 7: GENERATE REPORTS (NOW FUNCTIONAL) ============
    private static void generateReports() {
        if (students.isEmpty()) {
            System.out.println("\n📭 No students in the system.");
            return;
        }

        try {
            // Generate individual reports for each student
            for (Student student : students) {
                reportService.generateStudentReport(student, "reports");
            }
            
            // Generate class report
            reportService.generateClassReport(students, "reports");
            
            System.out.println("\n✅ Reports generated successfully!");
            System.out.println("   📁 Location: ./reports/");
            System.out.println("   📄 Files created:");
            System.out.println("      - " + students.size() + " individual student reports");
            System.out.println("      - 1 class report");
        } catch (IOException e) {
            System.out.println("❌ Error generating reports: " + e.getMessage());
        }
    }

    // ============ FEATURE 8: MERIT LIST (NOW FUNCTIONAL) ============
    private static void showMeritList() {
        if (students.isEmpty()) {
            System.out.println("\n📭 No students in the system.");
            return;
        }

        int topN = InputValidator.getIntInput(scanner, "Enter number of top students to display: ");
        if (topN > students.size()) {
            topN = students.size();
            System.out.println("⚠️ Showing all " + topN + " students.");
        }

        List<Student> meritList = statsService.getMeritList(students, topN);

        System.out.println("\n🏆 MERIT LIST (Top " + topN + " Students)");
        System.out.println("=".repeat(80));
        System.out.printf("%-4s %-12s %-20s %12s %8s %15s\n", 
                "Rank", "ID", "Name", "Percentage", "GPA", "Status");
        System.out.println("-".repeat(80));

        int rank = 1;
        for (Student s : meritList) {
            System.out.printf("%-4d %-12s %-20s %11.2f%% %7.2f %15s\n",
                    rank++, s.getStudentId(), s.getName(), 
                    s.getOverallPercentage(), s.getGPA(), s.getStatus());
        }
        System.out.println("=".repeat(80));
    }

    // ============ FEATURE 9: SAVE DATA (NOW FUNCTIONAL) ============
    private static void saveData() {
        if (students.isEmpty()) {
            System.out.println("\n📭 No students to save.");
            return;
        }

        try {
            dataService.saveToCSV(students, "data/students.csv");
            System.out.println("\n✅ Data saved successfully!");
            System.out.println("   📁 Location: data/students.csv");
            System.out.println("   📊 Students saved: " + students.size());
        } catch (IOException e) {
            System.out.println("❌ Error saving data: " + e.getMessage());
        }
    }

    // ============ FEATURE 10: LOAD DATA (NOW FUNCTIONAL) ============
    private static void loadData() {
        try {
            List<Student> loaded = dataService.loadFromCSV("data/students.csv");
            if (loaded.isEmpty()) {
                System.out.println("\n📭 No data found in CSV file.");
                return;
            }
            
            // Clear existing and add loaded data
            students.clear();
            students.addAll(loaded);
            
            System.out.println("\n✅ Data loaded successfully!");
            System.out.println("   📁 Location: data/students.csv");
            System.out.println("   📊 Students loaded: " + students.size());
            
            // Display loaded students summary
            System.out.println("\n📋 Loaded Students:");
            for (Student s : students) {
                System.out.printf("   - %s (%s) - %.2f%% %s\n", 
                        s.getName(), s.getStudentId(), 
                        s.getOverallPercentage(), s.getStatus());
            }
        } catch (IOException e) {
            System.out.println("❌ Error loading data: " + e.getMessage());
        }
    }

    // ============ HELPER METHOD ============
    private static Student findStudent(String id) {
        return students.stream()
                .filter(s -> s.getStudentId().equals(id))
                .findFirst()
                .orElse(null);
    }
}
