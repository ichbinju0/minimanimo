package minimanimo;

import minimanimo.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameLauncher {

    private static Scanner scanner; // For GameLaungherTest.java's test
    private static UserManager userManager;
    private static User currentUser;

    public static void main(String[] args) {
        List<MiniGame> gameList = new ArrayList<>(); // ArrayList to manage games
        gameList.add(new ChamChamCham());
        gameList.add(new RockPaperScissors());
        gameList.add(new NumberBaseball());
        gameList.add(new UpDown());
        // Later, if new game is added, just add it in here

        scanner = new Scanner(System.in); // For GameLaungherTest.java's test
        currentUser = null; // For GameLaungherTest.java's test
        // 1. Initialization
        userManager = new UserManager(); // Loads users.csv automatically
        System.out.println("========== MINIMANIMO GAME PLATFORM ==========");

        // 2. Authentication Flow (Loop until logged in)
        while (currentUser == null) {
            System.out.println("\n[Authentication Menu]");
            System.out.println("Enter 1 or 2: ");
            System.out.println("1. Login (Existing User)");
            System.out.println("2. Register (New User)");
            System.out.println("0. Exit");
            System.out.print(">> ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    login();
                    break;
                case "2":
                    register();
                    break;
                case "0":
                    System.out.println("Exiting application. Goodbye!");
                    return; // ends program
                default:
                    System.out.println("Invalid input. Please select 1 or 2.");
            }
        }

        // 3. Main Menu Loop
        boolean isRunning = true;
        while (isRunning) {
            // [2] Automatic Menu Display
            System.out.println("========================================");
            System.out.println("  Welcome, " + currentUser.getNickname() + "!");
            System.out.println("  Please select a game to play:");
            System.out.println("========================================");

            for (int i = 0; i < gameList.size(); i++) {
                // Print starting from 1 (Index 0 becomes Menu 1)
                System.out.println((i + 1) + ". " + gameList.get(i).getGameName());
            }
            System.out.println("0. Exit");
            System.out.print("Select >> ");

            String menuInput = scanner.nextLine().trim();
            MiniGame selectedGame = null;

            // 3. Main Menu Loop
            if (menuInput.equals("0")) {
                System.out.println("Exiting application. Goodbye!");
                isRunning = false;
                continue;
            }

            try {
                // Parse input string to integer
                int gameIndex = Integer.parseInt(menuInput) - 1; // Adjust for 0-based index

                // Check for valid range (0 to list size - 1)
                if (gameIndex >= 0 && gameIndex < gameList.size()) {
                    selectedGame = gameList.get(gameIndex);
                } else {
                    System.out.println("Invalid selection. Please select a valid number.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
                continue;
            }

            // 4. Game Execution & Scoring
            if (selectedGame != null) {
                // Run the game
                int newScore = selectedGame.startGame(currentUser, scanner);

                // [Refactor] 점수 갱신 로직을 별도 메서드로 분리 (캡슐화)
                handleScoreUpdate(selectedGame, newScore);

                // 5. Ranking Display
                userManager.showTop5(selectedGame.getGameName());
            }

        }
    }

    

    // --- Helper Methods ---

    private static void login() {
        System.out.print("Enter Nickname: ");
        String nickname = scanner.nextLine().trim();

        User user = userManager.getUser(nickname);
        if (user != null) {
            currentUser = user;
            System.out.println("Login Successful!");
        } else {
            System.out.println("User not found. Please register first.");
        }
    }

    private static void register() {
        while (true) {
            System.out.println("----------------------------------------------------");
            System.out.println("No spaces or special characters are allowed.");
            System.out.println("Nick name should be letters or with numbers only.");
            System.out.println("----------------------------------------------------");
            System.out.println("Valid NickName Example 1: soyeon (o)");
            System.out.println("Valid NickName Example2 : soyeon33 (o)");
            System.out.println("Valid NickName Example3 : 33 (o)");
            System.out.println("Invalid NickName Example 1: soyeon! (x)");
            System.out.println("Invalid NickName Example 2: so yeon (x)");
            System.out.print("Enter New Nickname OR enter 'q' to cancel: ");
            String nickname = scanner.nextLine().trim();

            // Check for cancellation command
            if (nickname.equalsIgnoreCase("q")) {
                System.out.println("Registration cancelled. Returning to main menu.");
                return;
            }

            // Validation 1: Empty Check
            if (nickname.isEmpty()) {
                System.out.println("Nickname cannot be empty.");
                continue;
            }

            // Validation 2: Regex (English letters and numbers only)
            if (!nickname.matches("^[a-zA-Z0-9]+$")) {
                System.out.println("Invalid format. No spaces or special characters allowed.");
                continue;
            }

            // Validation 3: Duplicate Check
            if (userManager.getUser(nickname) != null) {
                System.out.println("Nickname already exists. Please choose another.");
                continue;
            }

            // Create and Login
            userManager.addUser(nickname);
            currentUser = userManager.getUser(nickname);
            System.out.println("Registration successful! You are now logged in.");
            break;
        }
    }

    /**
     * Handles the logic for comparing and updating the user's high score.
     * Supports both "Higher is Better" and "Lower is Better" games.
     */
    private static void handleScoreUpdate(MiniGame game, int newScore) {
        String gameName = game.getGameName();
        int currentBestScore = currentUser.getScore(gameName);
        boolean isNewRecord = false;

        // 1. Check scoring type (Higher is better vs Lower is better)
        if (game.isLowerScoreBetter()) {
            // For games like UpDown: Lower score (fewer tries) is better.
            // If currentBestScore is 0, it means it's the first play (0 usually indicates
            // no record).
            if (currentBestScore == 0 || newScore < currentBestScore) {
                isNewRecord = true;
            }
        } else {
            // For games like RPS, Baseball: Higher score is better.
            if (newScore > currentBestScore) {
                isNewRecord = true;
            }
        }

        // 2. Update and Save if it's a new record
        if (isNewRecord) {
            currentUser.getScoreMap().put(gameName, newScore); // Still accessing Map, but logic is isolated
            userManager.saveUsers(); // Save changes to CSV
            System.out.println("New High Score! (" + newScore + ") Updated in database.");
        } else {
            System.out.println("Good game! (Your Score: " + newScore + ", Best Record: " + currentBestScore + ")");
        }
    }
}
