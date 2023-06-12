package com.hts.controller;
import com.hts.model.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
public class UserManagement {
    private static final String USER_PROFILES_FILE_PATH = "C:\\Users\\admin\\IdeaProjects\\Health-Managemment-System\\src\\main\\java\\com\\hts\\user_profiles.txt";

    private List<User> users;

    public UserManagement() {
        this.users = new ArrayList<>();
    }

    public User createUser(String username) {
        if (isUsernameTaken(username)) {
            System.out.println("Username already exists. Please choose a different username.");
            return null;
        }
        User user = new User(username);
        users.add(user);
        System.out.println("User created successfully.");
        return user;
    }

    public User loginUser(String username) {
        User foundUser = findUserByUsername(username);

        if (foundUser == null) {
            System.out.println("User not found. Please check your username or create a new user.");
            return null;
        } else {
            System.out.println("Logged in as: " + username);
            return foundUser;
        }
    }

    public void saveUser(User user) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(USER_PROFILES_FILE_PATH, true))) {
            writer.write(user.getUsername());
            writer.newLine();
            System.out.println("User profile saved successfully.");
        } catch (IOException e) {
            System.out.println("Error saving user profile: " + e.getMessage());
        }
    }

    public List<String> getSampleUsernames() {
        List<String> sampleUsernames = new ArrayList<>();
        sampleUsernames.add("Kelan");
        sampleUsernames.add("Javier");
        sampleUsernames.add("John");
        sampleUsernames.add("Hector");
        return sampleUsernames;
    }

    private boolean isUsernameTaken(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    private User findUserByUsername(String username) {
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    public User login(String username) {
        User foundUser = findUserByUsername(username);

        if (foundUser == null) {
            System.out.println("User not found. Please check your username or create a new user.");
            return null;
        } else {
            //System.out.println("Logged in as: " + username);
            return foundUser;
        }
    }

    public void loadUserProfiles() {
        try (BufferedReader reader = new BufferedReader(new FileReader(USER_PROFILES_FILE_PATH))) {
            String username;
            while ((username = reader.readLine()) != null) {
                User user = new User(username);
                users.add(user);
            }
            System.out.println("User profiles loaded successfully.");
        } catch (IOException e) {
            System.out.println("Error loading user profiles: " + e.getMessage());
        }
    }
}
