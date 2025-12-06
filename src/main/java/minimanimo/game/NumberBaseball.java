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
        System.out.println("==========================================");
        System.out.println(" Number Baseball - Player: " + user.getNickname());
        System.out.println("Rule: Guess 3 unique numbers between 1 and 9.");
        System.out.println("Start Life: " + MAX_LIFE + " (10 Chances)");
        System.out.println("------------------------------------------");

        generateSecretNumbers(); // Generate random secret numbers
        
        int attempts = 0;
        int life = MAX_LIFE;

        while (life > 0) {
            System.out.printf("[Life: %d] Enter 3 digits (or '0' to quit): ", life);
            
            String input = scanner.nextLine(); 

            if ("0".equals(input)) {
                System.out.println("Game stopped by user.");
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
                System.out.println(" Correct! You won in " + attempts + " attempts!");
                System.out.println("Secret Numbers: " + secretNumbers);
                // Return Final Score
                return MAX_LIFE - attempts + 1; 
            } else {
                displayResult(strikes, balls);
                life--; // Deduct life on failure
            }
         }

        System.out.println("Game Over - Out of Life! The answer was " + secretNumbers + ".");
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
            System.out.println("[Error] You must enter exactly 3 digits.");
            return false;
        }

        try {
            for (char c : input.toCharArray()) {
                int digit = Character.getNumericValue(c);
                if (digit < 1 || digit > 9) {
                    System.out.println("[Error] Only numbers between 1 and 9 are allowed (No 0).");
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("[Error] Input must be numeric.");
            return false;
        }

        // Check for duplicates
        Set<Character> uniqueChars = new HashSet<>();
        for (char c : input.toCharArray()) {
            uniqueChars.add(c);
        }
        if (uniqueChars.size() != 3) {
            System.out.println("[Error] Duplicate numbers are not allowed.");
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
        return new int[]{strikes, balls};
    }

    // 4. Display Results
    private void displayResult(int strikes, int balls) {
        if (strikes == 0 && balls == 0) {
            System.out.println("OUT!");
        } else {
            System.out.println(strikes + " Strike, " + balls + " Ball");
        }
    }
}