package com.hts.controller;

import com.hts.model.ExerciseEntry;
import com.hts.model.FoodEntry;
import com.hts.model.SleepRecord;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HealthDataAnalysis {
    private List<FoodEntry> foodEntries;
    private List<ExerciseEntry> exerciseEntries;
    private List<SleepRecord> sleepRecords;

    public HealthDataAnalysis(List<FoodEntry> foodEntries, List<ExerciseEntry> exerciseEntries, List<SleepRecord> sleepRecords) {
        this.foodEntries = foodEntries;
        this.exerciseEntries = exerciseEntries;
        this.sleepRecords = sleepRecords;
    }

    public void analyzeExerciseLog() {
        System.out.println("Exercise Log Analysis:");
        System.out.println("-----------------------");

        // Categorize exercises
        Map<String, List<ExerciseEntry>> exerciseCategories = categorizeExercises();

        // Generate summaries for each exercise category
        for (Map.Entry<String, List<ExerciseEntry>> entry : exerciseCategories.entrySet()) {
            String category = entry.getKey();
            List<ExerciseEntry> exercises = entry.getValue();

            int totalDuration = 0;
            int totalCaloriesBurned = 0;

            for (ExerciseEntry exercise : exercises) {
                totalDuration += exercise.getDurationInMinutes();
                totalCaloriesBurned += exercise.getCaloriesBurned();
            }

            System.out.println("Category: " + category);
            System.out.println("Total Duration: " + totalDuration + " minutes");
            System.out.println("Total Calories Burned: " + totalCaloriesBurned + " calories");
            System.out.println();
        }
    }

    private Map<String, List<ExerciseEntry>> categorizeExercises() {
        Map<String, List<ExerciseEntry>> exerciseCategories = new HashMap<>();

        for (ExerciseEntry exerciseEntry : exerciseEntries) {
            String category = exerciseEntry.getExerciseType();

            if (exerciseCategories.containsKey(category)) {
                exerciseCategories.get(category).add(exerciseEntry);
            } else {
                List<ExerciseEntry> exercises = new ArrayList<>();
                exercises.add(exerciseEntry);
                exerciseCategories.put(category, exercises);
            }
        }

        return exerciseCategories;
    }
}
