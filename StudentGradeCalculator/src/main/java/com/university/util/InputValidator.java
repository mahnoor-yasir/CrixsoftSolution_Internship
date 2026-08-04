package com.university.util;

import java.util.Scanner;

public class InputValidator {
    
    public static int getIntInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = scanner.nextInt();
                scanner.nextLine(); // consume newline
                if (value >= 0) return value;
                System.out.println("Please enter a positive number.");
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine(); // clear buffer
            }
        }
    }

    public static double getDoubleInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value >= 0) return value;
                System.out.println("Please enter a positive number.");
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    public static double getDoubleInputRange(Scanner scanner, String prompt, double min, double max) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = scanner.nextDouble();
                scanner.nextLine();
                if (value >= min && value <= max) return value;
                System.out.printf("Please enter a value between %.0f and %.0f.\n", min, max);
            } catch (Exception e) {
                System.out.println("Invalid input! Please enter a valid number.");
                scanner.nextLine();
            }
        }
    }

    public static String getNonEmptyInput(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) return input;
            System.out.println("Input cannot be empty!");
        }
    }

    public static String getOptionalInput(Scanner scanner, String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
