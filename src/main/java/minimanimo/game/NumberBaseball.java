package minimanimo.game;

import minimanimo.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * Issue #1: Implement Number Baseball Game Logic (Author: Kim Gyeongyoon)
 * - Guess 3 unique digits between 1 and 9.
 * - 10 Life points system (Deducts 1 point per failure).
 * - Final Score = 10 - Attempts taken.
 */

public class NumberBaseball implements MiniGame {

    private static final int MAX_LIFE = 10;
    private final List<Integer> secretNumbers = new ArrayList<>();

    @Override
    public String getGameName() {
        return "Baseball";
    }

    @Override
    public boolean isLowerScoreBetter() {
        // Return false because higher score (10 - attempts) is better.
        return false;
    }

    @Override
    public int startGame(User user, Scanner scanner) {
        System.out.println("\n\n");
        System.out.println("##########################################");
        System.out.println("##                                      ##");
        System.out.println("##          NUMBER  BASEBALL            ##");
        System.out.println("##                                      ##");
        System.out.println("##########################################");
        System.out.println("\n[PLAYER] " + user.getNickname());
        System.out.println("[RULE]   Guess 3 unique numbers (1-9).");
        System.out.println("[LIFE]   " + MAX_LIFE + " Chances\n");

        generateSecretNumbers(); // Generate random secret numbers

        int attempts = 0;
        int life = MAX_LIFE;

        while (life > 0) {
            System.out.println("------------------------------------------");
            System.out.printf("Current Life: %d / %d\n", life, MAX_LIFE);
            System.out.println("Enter 3 digits (e.g., 123) or '0' to Quit.");
            System.out.print(">> ");

            String input = scanner.nextLine().trim();

            if ("0".equals(input)) {
                System.out.println("=== Game Over! Final Score: 0 ===");
                return 0; // Return 0 on user quit
            }

            // 1. Validate Input (Internal implementation)
            if (!isValidInput(input)) {
                // If invalid, retry without deducting life.
                continue;
            }

            attempts++;
            int[] result = checkGuess(input);
            int strikes = result[0];
            int balls = result[1];

            if (strikes == 3) {
                System.out.println("\n==========================================");
                System.out.println("   (^_^) HOME RUN! You Won!");
                System.out.println("   Attempts: " + attempts + " | Secret: " + secretNumbers);
                System.out.println("==========================================");
                // Return Final Score
                return MAX_LIFE - attempts + 1;
            } else {
                displayResult(strikes, balls);
                life--; // Deduct life on failure
            }
        }

        System.out.println("\n==========================================");
        System.out.println("   [X] OUT... You ran out of life.");
        System.out.println("   [?] The Secret Number was: " + secretNumbers);
        System.out.println("==========================================");
        return 0; // Return 0 on failure
    }

    // --- Internal Logic ---

    // 1. Generate Secret Numbers (1~9, No duplicates)
    private void generateSecretNumbers() {
        List<Integer> availableNumbers = new ArrayList<>();
        for (int i = 1; i <= 9; i++) {
            availableNumbers.add(i);
        }
        Collections.shuffle(availableNumbers);

        secretNumbers.clear();
        for (int i = 0; i < 3; i++) {
            secretNumbers.add(availableNumbers.get(i));
        }
    }

    // 2. Validate Input (Length 3, Numeric, Range 1-9, No duplicates)
    private boolean isValidInput(String input) {
        if (input == null || input.length() != 3) {
            System.out.println("[ERROR] You must enter exactly 3 digits.");
            return false;
        }

        try {
            for (char c : input.toCharArray()) {
                int digit = Character.getNumericValue(c);
                if (digit < 1 || digit > 9) {
                    System.out.println("[ERROR] Only numbers between 1 and 9 are allowed (No 0).");
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("[ERROR] Input must be numeric.");
            return false;
        }

        // Check for duplicates
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : input.toCharArray()) {
            uniqueChars.add(c);
        }
        if (uniqueChars.size() != 3) {
            System.out.println("[ERROR] Duplicate numbers are not allowed.");
            return false;
        }

        return true;
    }

    // 3. Calculate Strikes and Balls
    private int[] checkGuess(String guess) {
        int strikes = 0;
        int balls = 0;

        for (int i = 0; i < 3; i++) {
            int guessedDigit = Character.getNumericValue(guess.charAt(i));

            if (guessedDigit == secretNumbers.get(i)) {
                strikes++;
            } else if (secretNumbers.contains(guessedDigit)) {
                balls++;
            }
        }
        return new int[] { strikes, balls };
    }

    // 4. Display Results
    private void displayResult(int strikes, int balls) {
        if (strikes == 0 && balls == 0) {
            System.out.println("   [ OUT ] Nothing matches!");
        } else {
            System.out.println(String.format("   [ %d Strike  |  %d Ball ]", strikes, balls));
        }
    }
}