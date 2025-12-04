package minimanimo;

import java.io.*;
import java.util.*;

public class UserManager {

    private static final String CSV_FILE = "users.csv";
    private static final String HEADER = "Nickname,ChamChamCham,RPS,Baseball,UpDown";

    private List<User> users;

    public UserManager() {
        this.users = new ArrayList<>();
        loadUsers();
    }

    public void loadUsers() {
        users.clear();
        File file = new File(CSV_FILE);

        if (!file.exists()) {
            System.out.println("[INFO] Data file not found. Initializing with empty list.");
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

                    user.updateScore("ChamChamCham", Integer.parseInt(data[1].trim()));
                    user.updateScore("RPS", Integer.parseInt(data[2].trim()));
                    user.updateScore("Baseball", Integer.parseInt(data[3].trim()));
                    user.updateScore("UpDown", Integer.parseInt(data[4].trim()));

                    users.add(user);

                } catch (NumberFormatException e) {
                    System.out.println("[WARN] Skipping invalid data format: " + line);
                }
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to load users: " + e.getMessage());
        }
    }

    public void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CSV_FILE))) {
            bw.write(HEADER);
            bw.newLine();

            for (User user : users) {
                String line = String.format("%s,%d,%d,%d,%d",
                        user.getNickname(),
                        user.getScore("ChamChamCham"),
                        user.getScore("RPS"),
                        user.getScore("Baseball"),
                        user.getScore("UpDown"));
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("[ERROR] Failed to save users: " + e.getMessage());
        }
    }

    public void addUser(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            System.out.println("[ERROR] Nickname cannot be empty.");
            return;
        }
        if (getUser(nickname) != null) {
            System.out.println("[ERROR] Nickname already exists: " + nickname);
            return;
        }
        users.add(new User(nickname));
        saveUsers();
    }

    public User getUser(String nickname) {
        for (User user : users) {
            if (user.getNickname().equals(nickname)) {
                return user;
            }
        }
        return null;
    }
}
