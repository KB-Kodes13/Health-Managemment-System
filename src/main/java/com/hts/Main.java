package com.hts;
import com.hts.controller.UserManagement;
import com.hts.model.FoodEntry;
import com.hts.model.ExerciseEntry;
import com.hts.model.SleepRecord;
import com.hts.model.User;

import java.time.LocalDate;
import java.util.Scanner;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {
    private static final String FOOD_ENTRIES_FILE_PATH = "C:\\Users\\admin\\IdeaProjects\\Health-Managemment-System\\src\\main\\java\\com\\hts\\food_entries.txt";
    private static final String EXERCISE_ENTRIES_FILE_PATH = "C:\\Users\\admin\\IdeaProjects\\Health-Managemment-System\\src\\main\\java\\com\\hts\\exercise_entries.txt";
    private static final String SLEEP_RECORDS_FILE_PATH = "C:\\Users\\admin\\IdeaProjects\\Health-Managemment-System\\src\\main\\java\\com\\hts\\sleep_records.txt";
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Check if the files exist, create them if they don't
        try {
            if (!Files.exists(Path.of(FOOD_ENTRIES_FILE_PATH))) {
                Files.createFile(Path.of(FOOD_ENTRIES_FILE_PATH));
            }
            if (!Files.exists(Path.of(EXERCISE_ENTRIES_FILE_PATH))) {
                Files.createFile(Path.of(EXERCISE_ENTRIES_FILE_PATH));
            }
            if (!Files.exists(Path.of(SLEEP_RECORDS_FILE_PATH))) {
                Files.createFile(Path.of(SLEEP_RECORDS_FILE_PATH));
            }
        } catch (IOException e) {
            System.out.println("Error creating files: " + e.getMessage());
            return;
        }

        System.out.println("Welcome to the Health Tracking System!");

        // Perform user login or registration
        User user = performUserManagement(scanner);
        if (user == null) {
            System.out.println("Exiting the application. Goodbye!");
            return;
        }

        //System.out.println("Logged in as: " + user.getUsername());

        // Perform health data input
        performHealthDataInput(user, scanner);

        // Analyze health data
        analyzeHealthData(user);

        System.out.println("Thank you for using the Health Tracking System. Goodbye!");
    }

    private static User performUserManagement(Scanner scanner) {
        UserManagement userManagement = new UserManagement();

        // Load user profiles from the text file
        userManagement.loadUserProfiles();

        System.out.println("Do you have an account? (Y/N)");
        String choice = scanner.nextLine();

        if (choice.equalsIgnoreCase("Y")) {
            System.out.println("Enter your username: ");
            String username = scanner.nextLine();

            // Log in with existing username
            User user = userManagement.loginUser(username);

            if (user == null) {
                System.out.println("Username not found. Exiting the application.");
                return null;
            } else {
                return user;
            }
        } else if (choice.equalsIgnoreCase("N")) {
            System.out.println("Enter a username: ");
            String username = scanner.nextLine();

            // Create a new user
            User newUser = userManagement.createUser(username);

            if (newUser == null) {
                System.out.println("Username already exists. Exiting the application.");
                return null;
            } else {
                // Save the newly created user
                userManagement.saveUser(newUser);
                return newUser;
            }
        } else {
            System.out.println("Invalid choice. Exiting the application.");
            return null;
        }
    }


    private static void performHealthDataInput(User user, Scanner scanner) {
        boolean exit = false;

        while (!exit) {
            System.out.println("Select an option:");
            System.out.println("1. Enter food intake");
            System.out.println("2. Log exercise activity");
            System.out.println("3. Log sleep record");
            System.out.println("4. Analyze health data");
            System.out.println("5. Exit");

            int option = scanner.nextInt();
            scanner.nextLine(); // Consume the remaining newline character

            switch (option) {
                case 1:
                    // Enter food intake
                    System.out.println("Enter food item: ");
                    String foodItem = scanner.nextLine();
                    System.out.println("Enter calories: ");
                    int calories = scanner.nextInt();
                    scanner.nextLine(); // Consume the remaining newline character

                    FoodEntry foodEntry = new FoodEntry(foodItem, calories, LocalDate.now());
                    user.addFoodEntry(foodEntry);
                    System.out.println("Food entry saved successfully.");
                    break;
                case 2:
                    // Log exercise activity
                    System.out.println("Enter exercise type: ");
                    String exerciseType = scanner.nextLine();
                    System.out.println("Enter duration (in minutes): ");
                    int durationInMinutes = scanner.nextInt();
                    System.out.println("Enter calories burned: ");
                    int caloriesBurned = scanner.nextInt();
                    scanner.nextLine(); // Consume the remaining newline character

                    ExerciseEntry exerciseEntry = new ExerciseEntry(exerciseType, durationInMinutes, caloriesBurned, LocalDate.now());
                    user.addExerciseEntry(exerciseEntry);
                    System.out.println("Exercise entry saved successfully.");
                    break;
                case 3:
                    // Log sleep record
                    System.out.println("Enter sleep time (yyyy-MM-dd HH:mm): ");
                    String sleepTime = scanner.nextLine();
                    System.out.println("Enter wakeup time (yyyy-MM-dd HH:mm): ");
                    String wakeupTime = scanner.nextLine();

                    SleepRecord sleepRecord = new SleepRecord(sleepTime, wakeupTime);
                    user.addSleepRecord(sleepRecord);
                    System.out.println("Sleep record saved successfully.");
                    break;
                case 4:
                    // Analyze health data
                    analyzeHealthData(user);
                    break;
                case 5:
                    // Exit the program
                    exit = true;
                    System.out.println("Exiting the application. ");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    private static void analyzeHealthData(User user) {
        System.out.println("Health Data Analysis");
        System.out.println("--------------------");

        // Daily Caloric Balance
        System.out.println("Daily Caloric Balance");
        System.out.println("---------------------");
        user.calculateDailyCaloricBalance();
        user.displayDailyCaloricBalance();

        // Sleep Analysis
        System.out.println("Sleep Analysis");
        System.out.println("--------------");
        user.calculateAverageSleepDuration();
        user.displaySleepAnalysis();

        // Exercise Log
        System.out.println("Exercise Log");
        System.out.println("------------");
        user.displayExerciseLog();

        // Health Summary
        System.out.println("Health Summary");
        System.out.println("--------------");
        user.generateHealthSummary();
    }
}
