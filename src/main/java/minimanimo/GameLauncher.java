package minimanimo;

import minimanimo.game.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class GameLauncher {

    private static Scanner scanner; // For GameLauncherTest.java's test
    private static UserManager userManager;
    private static User currentUser;

    public static void main(String[] args) {
        // [1] Game List Management
        // Using a List makes it easy to add new games later without modifying the menu logic.
        List<MiniGame> gameList = new ArrayList<>();
        gameList.add(new ChamChamCham());       // 1. ChamChamCham
        gameList.add(new RockPaperScissors());  // 2. RockPaperScissors
        gameList.add(new NumberBaseball());     // 3. NumberBaseball
        gameList.add(new UpDown());             // 4. UpDown

        scanner = new Scanner(System.in); // Initialized here for GameLauncherTest.java
        currentUser = null;               // Reset user for GameLauncherTest.java

        // 1. Initialization
        userManager = new UserManager(); // Loads users.csv automatically
        System.out.println("▗▖  ▗▖▗▄▄▄▖▗▖  ▗▖▗▄▄▄▖▗▖  ▗▖ ▗▄▖ ▗▖  ▗▖▗▄▄▄▖▗▖  ▗▖ ▗▄▖ ");
        System.out.println("▐▛▚▞▜▌  █  ▐▛▚▖▐▌  █  ▐▛▚▞▜▌▐▌ ▐▌▐▛▚▖▐▌  █  ▐▛▚▞▜▌▐▌ ▐▌");
        System.out.println("▐▌  ▐▌  █  ▐▌ ▝▜▌  █  ▐▌  ▐▌▐▛▀▜▌▐▌ ▝▜▌  █  ▐▌  ▐▌▐▌ ▐▌");
        System.out.println("▐▌  ▐▌▗▄█▄▖▐▌  ▐▌▗▄█▄▖▐▌  ▐▌▐▌ ▐▌▐▌  ▐▌▗▄█▄▖▐▌  ▐▌▝▚▄▞▘");
        System.out.println("                                                       ");
        System.out.println("                                                       ");
        System.out.println("                                                       ");

        // 2. Authentication Flow (Loop until logged in)
        while (currentUser == null) {
            System.out.println("\n[Authentication Menu]");
            System.out.println("───────────────────────────");
            System.out.println("1. Login (Existing User)");
            System.out.println("2. Register (New User)");
            System.out.println("───────────────────────────");
            System.out.println("0. Exit");
            // [UI Standardize] Consistent prompt style
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
                    System.out.println("Thanks for playing MINIMANIMO! See you again");
                    return; // Terminates the program
                default:
                    System.out.println("\u001B[31mInvalid input. Please select 1, 2 or 0.\u001B[0m");
            }
        }

        // 3. Main Menu Loop
        boolean isRunning = true;
        
        // Display welcome message only once when entering the main menu loop
        System.out.println("\nWelcome, " + currentUser.getNickname() + "!");

        while (isRunning) {
            // [2] Automatic Menu Display
            System.out.println("★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★");
            System.out.println("  Please select a game to play:");
            System.out.println("★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★");

            // Dynamically print the game list
            for (int i = 0; i < gameList.size(); i++) {
                // Print starting from 1 (Index 0 becomes Menu 1)
                System.out.println((i + 1) + ". " + gameList.get(i).getGameName());
            }
            System.out.println("0. Exit");
            // [UI Standardize] Removed "Select" to keep it consistent with ">>"
            System.out.print(">> ");

            String menuInput = scanner.nextLine().trim();
            MiniGame selectedGame = null;

            // Check for Exit Command
            if (menuInput.equals("0")) {
                System.out.println("Exiting application. Goodbye!");
                isRunning = false;
                continue;
            }

            // Logic Change: List Access instead of switch statement
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
                System.out.println("\u001B[31mInvalid input. Please enter a number.\u001B[0m");
                continue;
            }

            // 4. Game Execution & Scoring
            if (selectedGame != null) {

                // [Logic] Inner Loop: Keeps the user in the same game until they choose to leave
                while (true) {
		            clearScreen();  
					typeWriter("===== Starting " + selectedGame.getGameName() + " =====", 30);
                    // Run the game
                    int newScore = selectedGame.startGame(currentUser, scanner);

                    // Score Update (Encapsulated in a helper method)
                    handleScoreUpdate(selectedGame, newScore);

                    // Ranking Display
                    userManager.showTop5(selectedGame.getGameName());

                    // [UX Improvement] Next Step Selection
                    System.out.println("\n★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★");
                    System.out.println("What would you like to do next?");
                    System.out.println("───────────────────────────────");
                    System.out.println("1. Play Again (Same Game)");
                    System.out.println("2. Main Menu (Select Other Game)");
                    System.out.println("───────────────────────────────");
                    System.out.println("0. Exit Program");
                    System.out.print(">> ");

                    String nextChoice = scanner.nextLine().trim();

                    if (nextChoice.equals("1")) {
                        System.out.println("\nStarting " + selectedGame.getGameName() + " again...");
                        continue; // Restart the inner loop (Game restarts)
                    } else if (nextChoice.equals("2")) {
		                    clearScreen();
                        System.out.println("Returning to Main Menu...");
                        break; // Break inner loop -> Go back to Main Menu loop
                    } else if (nextChoice.equals("0")) {
                        System.out.println("Exiting application. Goodbye!");
                        isRunning = false; // Stop the outer loop
                        break; // Break inner loop
                    } else {
                        System.out.println("\u001B[31mInvalid input. Returning to Main Menu.\u001B[0m");
                        break;
                    }
                }
            }
        }
    }

    // --- Helper Methods ---

    private static void login() {
        // [UI Standardize] Using ">>" instead of ":"
        System.out.print("Enter Nickname >> ");
        String nickname = scanner.nextLine().trim();

        User user = userManager.getUser(nickname);
        if (user != null) {
            currentUser = user;
            typeWriter("Login Successful!",20);
        } else {
            System.out.println("User not found. Please register first.");
        }
    }

    private static void register() {
        while (true) {
            System.out.println("★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★");
            System.out.println("No spaces or special characters are allowed.");
            System.out.println("Nickname should be letters or numbers only.");
            System.out.println("★━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━━★");
            System.out.println("Valid NickName Example 1: soyeon (o)");
            System.out.println("Valid NickName Example 2: soyeon33 (o)");
            System.out.println("Valid NickName Example 3: 33 (o)");
            System.out.println("Invalid NickName Example 1: soyeon! (x)");
            System.out.println("Invalid NickName Example 2: so yeon (x)");
            
            // [UI Standardize] using ">>" and clearer prompt
            System.out.print("Enter New Nickname (or 'q' to cancel) >> ");
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
            // If currentBestScore is 0, it means it's the first play (0 usually indicates no record).
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
            currentUser.getScoreMap().put(gameName, newScore);
            userManager.saveUsers(); // Save changes to CSV
            typeWriter("\u001B[32mNew High Score! (" + newScore + ") Updated in database.\u001B[0m",35);
        } else {
            System.out.println("Good game! (Your Score: " + newScore + ", Best Record: " + currentBestScore + ")");
        }
    }
    
    private static void clearScreen() {
			  for (int i = 0; i < 50; i++) System.out.println();
		}

    // Typing Effect Helper Method
    public static void typeWriter(String text, int delay) {
        for (char c : text.toCharArray()) {
            System.out.print(c);
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        System.out.println();
    }
}