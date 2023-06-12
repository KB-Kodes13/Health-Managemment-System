package com.hts.model;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
public class FoodEntry {
    private String foodItem;
    private int calories;
    private LocalDate date;

    public FoodEntry(String foodItem, int calories, LocalDate date) throws IllegalArgumentException {
        setFoodItem(foodItem);
        setCalories(calories);
        setDate(date);
    }

    // Getters and setters

    public String getFoodItem() {
        return foodItem;
    }

    public void setFoodItem(String foodItem) throws IllegalArgumentException {
        if (foodItem == null || foodItem.trim().isEmpty()) {
            throw new IllegalArgumentException("Food item cannot be empty.");
        }
        this.foodItem = foodItem;
    }

    public int getCalories() {
        return calories;
    }

    public void setCalories(int calories) throws IllegalArgumentException {
        if (calories < 0) {
            throw new IllegalArgumentException("Calories must be a non-negative value.");
        }
        this.calories = calories;
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

    public static FoodEntry readFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) {
                String[] data = line.split(",");
                String foodItem = data[0].trim();
                int calories = Integer.parseInt(data[1].trim());
                LocalDate date = LocalDate.parse(data[2].trim());
                return new FoodEntry(foodItem, calories, date);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return null;
    }

    @Override
    public String toString() {
        return foodItem + "," + calories + "," + date;
    }

    public void calculateCaloricBalance() {
    }
}
