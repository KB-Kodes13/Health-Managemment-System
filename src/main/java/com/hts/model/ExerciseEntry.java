package com.hts.model;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
public class ExerciseEntry {
    private String exerciseType;
    private int durationInMinutes;
    private int caloriesBurned;
    private LocalDate date;

    public ExerciseEntry(String exerciseType, int durationInMinutes, int caloriesBurned, LocalDate date) throws IllegalArgumentException {
        setExerciseType(exerciseType);
        setDurationInMinutes(durationInMinutes);
        setCaloriesBurned(caloriesBurned);
        setDate(date);
    }

    // Getters and setters

    public String getExerciseType() {
        return exerciseType;
    }

    public void setExerciseType(String exerciseType) throws IllegalArgumentException {
        if (exerciseType == null || exerciseType.trim().isEmpty()) {
            throw new IllegalArgumentException("Exercise type cannot be empty.");
        }
        this.exerciseType = exerciseType;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public void setDurationInMinutes(int durationInMinutes) throws IllegalArgumentException {
        if (durationInMinutes < 0) {
            throw new IllegalArgumentException("Duration must be a non-negative value.");
        }
        this.durationInMinutes = durationInMinutes;
    }

    public int getCaloriesBurned() {
        return caloriesBurned;
    }

    public void setCaloriesBurned(int caloriesBurned) throws IllegalArgumentException {
        if (caloriesBurned < 0) {
            throw new IllegalArgumentException("Calories burned must be a non-negative value.");
        }
        this.caloriesBurned = caloriesBurned;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) throws IllegalArgumentException {
        if (date == null) {
            throw new IllegalArgumentException("Date cannot be null.");
        }
        this.date = date;
    }
    public void writeToFile(String filePath) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static ExerciseEntry readFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) {
                String[] data = line.split(",");
                String exerciseType = data[0].trim();
                int durationInMinutes = Integer.parseInt(data[1].trim());
                int caloriesBurned = Integer.parseInt(data[2].trim());
                LocalDate date = LocalDate.parse(data[3].trim());
                return new ExerciseEntry(exerciseType, durationInMinutes, caloriesBurned, date);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return exerciseType + "," + durationInMinutes + "," + caloriesBurned + "," + date;
    }


    public void calculateCaloricBalance() {
    }

}
