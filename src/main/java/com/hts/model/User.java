package com.hts.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
public class User {
    private String username;
    private List<FoodEntry> foodEntries;
    private List<ExerciseEntry> exerciseEntries;
    private List<SleepRecord> sleepRecords;

    public User(String username) {
        this.username = username;
        this.foodEntries = new ArrayList<>();
        this.exerciseEntries = new ArrayList<>();
        this.sleepRecords = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public void addFoodEntry(FoodEntry foodEntry) {
        foodEntries.add(foodEntry);
    }

    public void addExerciseEntry(ExerciseEntry exerciseEntry) {
        exerciseEntries.add(exerciseEntry);
    }

    public void addSleepRecord(SleepRecord sleepRecord) {
        sleepRecords.add(sleepRecord);
    }

    public void calculateDailyCaloricBalance() {
        for (FoodEntry foodEntry : foodEntries) {
            foodEntry.calculateCaloricBalance();
        }

        for (ExerciseEntry exerciseEntry : exerciseEntries) {
            exerciseEntry.calculateCaloricBalance();
        }
    }

    public void displayDailyCaloricBalance() {
        for (FoodEntry foodEntry : foodEntries) {
            System.out.println(foodEntry);
        }

        for (ExerciseEntry exerciseEntry : exerciseEntries) {
            System.out.println(exerciseEntry);
        }
    }

    public void calculateAverageSleepDuration() {
        int totalSleepDuration = 0;
        for (SleepRecord sleepRecord : sleepRecords) {
            totalSleepDuration += sleepRecord.getDuration();
        }

        if (!sleepRecords.isEmpty()) {
            int averageSleepDuration = totalSleepDuration / sleepRecords.size();
            System.out.println("Average sleep duration: " + averageSleepDuration + " hours");
        }
    }

    public void displaySleepAnalysis() {
        for (SleepRecord sleepRecord : sleepRecords) {
            System.out.println(sleepRecord);
        }
    }

    public void displayExerciseLog() {
        for (ExerciseEntry exerciseEntry : exerciseEntries) {
            System.out.println(exerciseEntry);
        }
    }

    public void generateHealthSummary() {
        int totalCaloriesConsumed = 0;
        int totalCaloriesBurned = 0;
        Map<String, Integer> exerciseCategories = new HashMap<>();

        for (FoodEntry foodEntry : foodEntries) {
            totalCaloriesConsumed += foodEntry.getCalories();
        }

        for (ExerciseEntry exerciseEntry : exerciseEntries) {
            totalCaloriesBurned += exerciseEntry.getCaloriesBurned();

            String exerciseType = exerciseEntry.getExerciseType();
            exerciseCategories.put(exerciseType, exerciseCategories.getOrDefault(exerciseType, 0) + 1);
        }

        System.out.println("Total calories consumed: " + totalCaloriesConsumed);
        System.out.println("Total calories burned: " + totalCaloriesBurned);

        int mostCommonExercises = 0;
        String mostCommonExerciseType = "";

        for (Map.Entry<String, Integer> entry : exerciseCategories.entrySet()) {
            String exerciseType = entry.getKey();
            int exerciseCount = entry.getValue();

            if (exerciseCount > mostCommonExercises) {
                mostCommonExercises = exerciseCount;
                mostCommonExerciseType = exerciseType;
            }
        }

        System.out.println("Most common exercise type: " + mostCommonExerciseType);
    }

    public void displayHealthSummary() {
        // Call the necessary methods to display health summary
        calculateDailyCaloricBalance();
        calculateAverageSleepDuration();
        displayExerciseLog();
        generateHealthSummary();
    }
}
