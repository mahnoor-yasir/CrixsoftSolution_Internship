package com.university.service;

import com.university.model.Student;
import java.util.*;

public class StatisticsService {

    public double getMinPercentage(List<Student> students) {
        return students.stream().mapToDouble(Student::getOverallPercentage).min().orElse(0.0);
    }

    public double getMaxPercentage(List<Student> students) {
        return students.stream().mapToDouble(Student::getOverallPercentage).max().orElse(0.0);
    }

    public double getMeanPercentage(List<Student> students) {
        return students.stream().mapToDouble(Student::getOverallPercentage).average().orElse(0.0);
    }

    public double getMedianPercentage(List<Student> students) {
        List<Double> percentages = students.stream()
                .map(Student::getOverallPercentage)
                .sorted()
                .toList();
        
        int size = percentages.size();
        if (size == 0) return 0.0;
        if (size % 2 == 1) return percentages.get(size / 2);
        return (percentages.get(size / 2 - 1) + percentages.get(size / 2)) / 2.0;
    }

    public double getStandardDeviation(List<Student> students) {
        double mean = getMeanPercentage(students);
        double variance = students.stream()
                .mapToDouble(s -> Math.pow(s.getOverallPercentage() - mean, 2))
                .average()
                .orElse(0.0);
        return Math.sqrt(variance);
    }

    public Map<String, Long> getGradeDistribution(List<Student> students) {
        Map<String, Long> distribution = new LinkedHashMap<>();
        for (Student student : students) {
            String grade = student.getGrade().substring(0, 2);
            distribution.put(grade, distribution.getOrDefault(grade, 0L) + 1);
        }
        return distribution;
    }

    public List<Student> getMeritList(List<Student> students, int topN) {
        return students.stream()
                .sorted((s1, s2) -> Double.compare(s2.getOverallPercentage(), s1.getOverallPercentage()))
                .limit(topN)
                .toList();
    }

    public void displayStatistics(List<Student> students) {
        if (students.isEmpty()) {
            System.out.println("No students to display statistics.");
            return;
        }

        System.out.println("\n📈 CLASS STATISTICS");
        System.out.println("=".repeat(50));
        System.out.printf("  Total Students     : %d\n", students.size());
        System.out.printf("  Minimum Percentage : %.2f%%\n", getMinPercentage(students));
        System.out.printf("  Maximum Percentage : %.2f%%\n", getMaxPercentage(students));
        System.out.printf("  Mean Percentage    : %.2f%%\n", getMeanPercentage(students));
        System.out.printf("  Median Percentage  : %.2f%%\n", getMedianPercentage(students));
        System.out.printf("  Standard Deviation : %.2f%%\n", getStandardDeviation(students));

        System.out.println("\n  📊 Grade Distribution:");
        Map<String, Long> distribution = getGradeDistribution(students);
        for (Map.Entry<String, Long> entry : distribution.entrySet()) {
            System.out.printf("    %-6s : %d students\n", entry.getKey(), entry.getValue());
        }
    }
}
