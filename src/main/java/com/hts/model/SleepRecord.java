package com.hts.model;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
public class SleepRecord {
    private LocalDateTime sleepTime;
    private LocalDateTime wakeupTime;

    public SleepRecord(LocalDateTime sleepTime, LocalDateTime wakeupTime) throws IllegalArgumentException {
        setSleepTime(sleepTime);
        setWakeupTime(wakeupTime);
    }

    public SleepRecord(String sleepTime, String wakeupTime) {
        setSleepTime(LocalDateTime.parse(sleepTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
        setWakeupTime(LocalDateTime.parse(wakeupTime, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
    }

    // Getters and setters

    public LocalDateTime getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(LocalDateTime sleepTime) throws IllegalArgumentException {
        if (sleepTime == null) {
            throw new IllegalArgumentException("Sleep time cannot be null.");
        }
        this.sleepTime = sleepTime;
    }

    public LocalDateTime getWakeupTime() {
        return wakeupTime;
    }

    public void setWakeupTime(LocalDateTime wakeupTime) throws IllegalArgumentException {
        if (wakeupTime == null) {
            throw new IllegalArgumentException("Wakeup time cannot be null.");
        }
        if (wakeupTime.isBefore(sleepTime)) {
            throw new IllegalArgumentException("Wakeup time cannot be before sleep time.");
        }
        this.wakeupTime = wakeupTime;
    }

    // Calculate total sleep duration in hours
    public long calculateSleepDuration() {
        return sleepTime.until(wakeupTime, ChronoUnit.HOURS);
    }

    public void writeToFile(String filePath) {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath), StandardOpenOption.CREATE, StandardOpenOption.APPEND)) {
            writer.write(toString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }

    public static SleepRecord readFromFile(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line = reader.readLine();
            if (line != null) {
                String[] data = line.split(",");
                LocalDateTime sleepTime = LocalDateTime.parse(data[0].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                LocalDateTime wakeupTime = LocalDateTime.parse(data[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
                return new SleepRecord(sleepTime, wakeupTime);
            }
        } catch (IOException e) {
            System.out.println("Error reading from file: " + e.getMessage());
        }
        return null;
    }

    public int getDuration() {
        return (int) calculateSleepDuration();
    }

    @Override
    public String toString() {
        return sleepTime + "," + wakeupTime;
    }
}
