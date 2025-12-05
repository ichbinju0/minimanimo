package minimanimo;


import minimanimo.game.*; 
import java.util.Scanner;

public class GameLauncher {

    // Scanner must be static to prevent closing System.in
    private static final Scanner scanner = new Scanner(System.in);
    private static UserManager userManager;
    private static User currentUser;

    public static void main(String[] args) {
        // 1. Initialization
        userManager = new UserManager(); // Loads users.csv automatically
        System.out.println("========== MINIMANIMO GAME PLATFORM ==========");

        // 2. Authentication Flow (Loop until logged in)
        while (currentUser == null) {
            System.out.println("\n[Authentication Menu]");
            System.out.println("Enter 1 or 2: ");
            System.out.println("1. Login (Existing User)");
            System.out.println("2. Register (New User)");
            System.out.print(">> ");

            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    login();
                    break;
                case "2":
                    register();
                    break;
                default:
                    System.out.println("⚠️ Invalid input. Please select 1 or 2.");
            }
        }

        // 3. Main Menu Loop
        boolean isRunning = true;
        while (isRunning) {
            System.out.println("\n========================================");
            System.out.println("  Welcome, " + currentUser.getNickname() + "!");
            System.out.println("  Please select a game to play:");
            System.out.println("========================================");
            System.out.println("1. Cham Cham Cham");
            System.out.println("2. Rock Paper Scissors");
            System.out.println("3. Number Baseball");
            System.out.println("4. Up Down");
            System.out.println("0. Exit");
            System.out.print("Select >> ");

            String menuInput = scanner.nextLine().trim();
            MiniGame selectedGame = null;

            switch (menuInput) {
                case "1":
                    selectedGame = new ChamChamCham();
                    break;
                case "2":
                    selectedGame = new RockPaperScissors();
                    break;
                case "3":
                    selectedGame = new NumberBaseball();
                    break;
                case "4":
                    selectedGame = new UpDown();
                    break;
                case "0":
                    System.out.println("Exiting application. Goodbye!");
                    isRunning = false;
                    continue; // Break the loop
                default:
                    System.out.println("Invalid selection. Please try again.");
                    continue;
            }

            // 4. Game Execution & Scoring
            if (selectedGame != null) {
                // Run the game
                int newScore = selectedGame.startGame(currentUser, scanner);
                
                // Get the game name (e.g., "RPS", "ChamChamCham")
                String gameName = selectedGame.getGameName();

                // Logic: Compare with best score and update if necessary
                // (Assumes higher score is better. If 'lower is better', logic needs inversion)
                int currentBestScore = currentUser.getScore(gameName);

                // Note: You can delegate this logic to User.updateScore if implemented there.
                // Here, we explicitly check to satisfy the Requirement "Compare with user's best score".
                if (newScore > currentBestScore) {
                     // Assuming User has a method to update the score map
                     currentUser.getScoreMap().put(gameName, newScore); 
                     System.out.println("New High Score! Updated in database.");
                } else {
                     System.out.println("Good game! (Best Score: " + currentBestScore + ")");
                }

                // Save to CSV immediately
                userManager.saveUsers();

                // 5. Ranking Display
                // Request UserManager to display the leaderboard
                userManager.showTop5(gameName); 
            }
        }
        
        scanner.close();
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
            System.out.print("Enter New Nickname (Letters/Numbers only): ");
            String nickname = scanner.nextLine().trim();

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
}
