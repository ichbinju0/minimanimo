package minimanimo.game;

import minimanimo.User;
import java.util.Random;
import java.util.Scanner;

public class UpDown implements MiniGame {
    @Override
    public String getGameName() {
        return "UpDown";
    }

    @Override // Starts the game for a given user and returns the score.
    public int startGame(User user, Scanner scanner) {
        System.out.println("\n\n");
        System.out.println("##########################################");
        System.out.println("##                                      ##");
        System.out.println("##           UP  -  AND  -  DOWN        ##");
        System.out.println("##                                      ##");
        System.out.println("##########################################");
        System.out.println("[RULE] Guess the number between 1 and 100.");
        System.out.println("[TIP]  You have 10 attempts. Enter '0' to quit.\n");

        Random random = new Random();
        int targetNumber = random.nextInt(100) + 1;
        int attempts = 0;
        int maxAttempts = 10;

        while (attempts < maxAttempts) {
            System.out.println("------------------------------------------");
            System.out.println("Attempt: " + (attempts + 1) + " / " + maxAttempts);
            System.out.println("Enter your guess (1-100): ");
            System.out.print(">> "); // Prompt user for input
            String input = scanner.nextLine().trim();
            if (input.equals("0")) {
                System.out.println(" Game stopped by user.");
                System.out.println("=== Game Over! Final Score: 0 ===");
                return 0;
            }

            if (!input.matches("\\d+")) { // Validate input is a number
                System.out.println("[ERROR] Invalid input. Please enter a number.");
                continue;
            }

            int guess = Integer.parseInt(input);
            if (guess < 1 || guess > 100) { // Validate input range
                System.out.println("[ERROR] Please guess a number between 1 and 100.");
                continue;
            }

            attempts++;
            if (guess == targetNumber) { // Correct guess
                System.out.println("\n==========================================");
                System.out.println("   (O_O) CORRECT! You found the number!");
                System.out.println("==========================================");
                int score = maxAttempts - attempts + 1;
                System.out.println("=== Game Over! Final Score: " + score + " ===");
                return score;
            } else if (guess < targetNumber) { // Provide feedback
                System.out.println("   [ UP ]  The number is HIGHER! (^^)");
            } else {
                System.out.println("   [ DOWN ] The number is LOWER! (vv)");
            }

        }

        // Out of attempts
        System.out.println("\n==========================================");
        System.out.println("   (T_T) Failed... You used all attempts.");
        System.out.println("   The correct number was: " + targetNumber);
        System.out.println("==========================================");

        System.out.println("=== Game Over! Final Score: 0 ===");
        return 0;
    }

    @Override // Indicates whether a lower score means a better performance.
    public boolean isLowerScoreBetter() {
        return false;
    }
}
