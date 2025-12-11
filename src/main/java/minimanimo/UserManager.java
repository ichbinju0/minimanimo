package minimanimo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private String csvFile;
    private static final String[] GAMES = { "ChamChamCham", "RPS", "Baseball", "UpDown" };
    private static final String HEADER = "Nickname,ChamChamCham,RPS,Baseball,UpDown";

    private List<User> users;

    public UserManager() { // Constructor
        this("users.csv");
    }

    public UserManager(String testFileName) {
        this.csvFile = testFileName; // Convert to use test file
        this.users = new ArrayList<>();
        loadUsers();
    }

    public void loadUsers() { // Load users from CSV
        users.clear();
        File file = new File(csvFile);

        if (!file.exists()) { // If file doesn't exist, initialize empty list
            System.out.println("\u001B[33m[INFO]\u001B[0m Data file not found. Initializing with empty list.");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            boolean isHeader = true;

            while ((line = br.readLine()) != null) {
                if (isHeader) {
                    isHeader = false;
                    continue;
                }

                String[] data = line.split(",");
                if (data.length < 5)
                    continue;

                try {
                    String nickname = data[0].trim();
                    User user = new User(nickname);

                    for (int i = 0; i < GAMES.length; i++) {
                        if (i + 1 < data.length) {
                            user.getScoreMap().put(GAMES[i], Integer.parseInt(data[i + 1].trim()));
                        }
                    }

                    users.add(user);

                } catch (NumberFormatException e) {
                    System.out.println("\u001B[33m[WARN]\u001B[0m  Skipping invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("\u001B[31m[ERROR]\u001B[0m  Failed to load users: " + e.getMessage());
        }
    }

    public void saveUsers() { // Save users to CSV
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(csvFile))) {
            bw.write(HEADER);
            bw.newLine();

            for (User user : users) {
                StringBuilder line = new StringBuilder(user.getNickname());
                for (String game : GAMES) {
                    line.append(",").append(user.getScore(game));
                }
                bw.write(line.toString());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("\u001B[31m[ERROR]\u001B[0m  Failed to save users: " + e.getMessage());
        }
    }

    public void addUser(String nickname) { // Add new user
        if (nickname == null || nickname.trim().isEmpty()) {
            System.out.println("\u001B[31m[ERROR]\u001B[0m  Nickname cannot be empty.");
            return;
        }
        if (getUser(nickname) != null) {
            System.out.println("\u001B[31m[ERROR]\u001B[0m  Nickname already exists:  " + nickname);
            return;
        }
        users.add(new User(nickname));
        saveUsers();
    }

    public User getUser(String nickname) { // Retrieve user by nickname
        for (User user : users) {
            if (user.getNickname().equals(nickname)) {
                return user;
            }
        }
        return null;
    }

    public int getUserCount() { // Get total user count
        return users.size();
    }

    public void showTop5(String gameName) { // Display top 5 users for a game
        if (users.isEmpty()) {
            System.out.println("\u001B[34m[INFO]\u001B[0m  No users available.");
            return;
        }
        List<User> sortedUsers = new ArrayList<>(this.users);
        sortedUsers.sort((u1, u2) -> {
            int score1 = u1.getScore(gameName);
            int score2 = u2.getScore(gameName);
            return score2 - score1;
        });
        System.out.println("★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★");
        System.out.println("\u001B[34m[INFO]\u001B[0m  Top 5 users for " + gameName + ":");
        System.out.println("★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★");

        System.out.println("  Rank  |     Nickname     |  Score ");
        System.out.println("--------------------------------------");

        int limit = Math.min(sortedUsers.size(), 5);
        for (int i = 0; i < limit; i++) {
            User u = sortedUsers.get(i);
            System.out.printf("   %2d   |  %-14s  |  %5d \n",
                    (i + 1), u.getNickname(), u.getScore(gameName));
        }
        System.out.println("★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★");
    }
}