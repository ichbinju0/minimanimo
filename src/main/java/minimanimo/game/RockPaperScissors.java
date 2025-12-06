package minimanimo.game;

import minimanimo.User;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors implements MiniGame {

    private final Random random = new Random();

    // Define constants for readability and maintainability
    private static final int ROCK = 1;
    private static final int PAPER = 2;
    private static final int SCISSORS = 3;

    private static final int WIN_SCORE = 3;
    private static final int DRAW_SCORE = 1;

    @Override
    public String getGameName() {
        return "RPS";
    }

    @Override
    public boolean isLowerScoreBetter() {
        return false;
    }

    @Override
    public int startGame(User user, Scanner scanner) {
        System.out.println("=== Starting Rock Paper Scissors (RPS)! ===");
        System.out.printf("Rules: Win (+%d points), Draw (+%d point), Lose (Game Over)!\n", WIN_SCORE, DRAW_SCORE);

        int currentScore = 0;
        
        while (true) {
            System.out.println("\n------------------------------");
            System.out.println("Current Score: " + currentScore);
            
            System.out.println("Enter your choice:");
            System.out.print("1. Rock  2. Paper  3. Scissors (0. Quit RPS) : ");

            int userChoice;
            
            // Check for non-numeric input and range 1-3
            try {
                // This prevents "Invalid input" errors in the main menu after the game ends.
                String input = scanner.nextLine().trim(); 
                
                // Quit if input is 0
                if (input.equals("0")) {
                    System.out.println("\n Game stopped by user.");
                    System.out.println("=== Game Over! Final Score: " + currentScore + " ===");
                    return currentScore; 
                }

                // Handle empty input (just Enter)
                if (input.isEmpty()) {
                    System.out.println("You only typed enter. Please choose 1, 2, or 3.");
                    continue;
                }

                userChoice = Integer.parseInt(input);

                if (userChoice < ROCK || userChoice > SCISSORS) {
                    System.out.println("Invalid input. Please choose 1, 2, or 3.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number!");
                continue;
            }

            // Logic: 0,1,2 -> Convert to 1,2,3 for consistency
            int computerChoice = random.nextInt(3) + 1;

            printChoices(userChoice, computerChoice);

            // [Game Logic] Determine Winner
            if (userChoice == computerChoice) {
                System.out.printf("It's a draw! (+%d point)\n", DRAW_SCORE);
                currentScore += DRAW_SCORE; // Draw: +1 point
            } else if (isWin(userChoice, computerChoice)) {
                System.out.printf("You won! (+%d points)\n", WIN_SCORE);
                currentScore += WIN_SCORE; // Win: +3 points
            } else {
                System.out.println("You lost... Game Over!");
                break;
            }
        }

        System.out.println("\n=== Game Over! Final Score: " + currentScore + " ===");

        return currentScore;
    }

    // Helper method to check if the user won
    private boolean isWin(int user, int computer) {
        // 1:Rock, 2:Paper, 3:Scissors
        // Rock(1) vs Scissors(3) -> Win
        // Paper(2) vs Rock(1)    -> Win
        // Scissors(3) vs Paper(2)-> Win
        return (user == ROCK && computer == SCISSORS) ||
               (user == PAPER && computer == ROCK) ||
               (user == SCISSORS && computer == PAPER);
    }

    // Helper method to display choices
    private void printChoices(int user, int computer) {
        System.out.println("---------------------------------------------------------------------------");
        System.out.println("Me: " + choiceToString(user) + " VS Computer: " + choiceToString(computer));
        System.out.println("---------------------------------------------------------------------------");
    }

    private String choiceToString(int choice) {
        switch (choice) {
            case ROCK: return "Rock";
            case PAPER: return "Paper";
            case SCISSORS: return "Scissors";
            default: return "Unknown";
        }
    }
}