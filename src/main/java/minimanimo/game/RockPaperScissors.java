package minimanimo.game;

import java.util.Random;
import java.util.Scanner;

import minimanimo.User;

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
        System.out.println("\n\n");
        System.out.println("##########################################");
        System.out.println("##                                      ##");
        System.out.println("##        ROCK - PAPER - SCISSORS       ##");
        System.out.println("##                                      ##");
        System.out.println("##########################################");
        System.out.printf("[RULE] Win (+%d), Draw (+%d), Lose (Game Over)\n", WIN_SCORE, DRAW_SCORE);
        System.out.println("[TIP]  Enter '0' to quit.\n");

        int currentScore = 0;

        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("Current Score: " + currentScore);
            System.out.println("Choose your weapon:");
            System.out.println("[ 1. (●) Rock  |  2. [□] Paper  |  3. [▲] Scissors ]");
            System.out.println("(0. Quit)");
            System.out.print(">> ");

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
                    System.out.println("[ERROR] Input cannot be empty.");
                    continue;
                }

                userChoice = Integer.parseInt(input);

                if (userChoice < ROCK || userChoice > SCISSORS) {
                    System.out.println("[ERROR] Invalid input. Please choose 1, 2, or 3.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("[ERROR] Please enter a number!");
                continue;
            }

            // Logic: 0,1,2 -> Convert to 1,2,3 for consistency
            int computerChoice = random.nextInt(3) + 1;

            System.out.println("\nRock...");
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
            }
            System.out.println("Paper...");
            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
            }
            System.out.println("Scissors!!\n");

            printChoices(userChoice, computerChoice);

            // [Game Logic] Determine Winner
            if (userChoice == computerChoice) {
                System.out.printf(" ( -_-) It's a draw! (+%d point)\n", DRAW_SCORE);
                currentScore += DRAW_SCORE; // Draw: +1 point
            } 
            else if (isWin(userChoice, computerChoice)) {
                System.out.printf(" (^_^) You won! (+%d points)\n", WIN_SCORE);
                currentScore += WIN_SCORE; // Win: +3 points
            } else {
                System.out.println("\n==========================================");
                System.out.println("   (T_T) You lost... Game Over!");
                System.out.println("==========================================");
                break;
            }
        }

        System.out.println("=== Game Over! Final Score: " + currentScore + " ===");

        return currentScore;
    }

    // Helper method to check if the user won
    private boolean isWin(int user, int computer) {
        // 1:Rock, 2:Paper, 3:Scissors
        // Rock(1) vs Scissors(3) -> Win
        // Paper(2) vs Rock(1) -> Win
        // Scissors(3) vs Paper(2)-> Win
        return (user == ROCK && computer == SCISSORS) ||
                (user == PAPER && computer == ROCK) ||
                (user == SCISSORS && computer == PAPER);
    }

    // Helper method to display choices
    private void printChoices(int user, int computer) {
        System.out.println(String.format("   Me: %-12s  VS  %12s :Com",
                choiceToString(user), choiceToString(computer)));
        System.out.println("");
    }

    private String choiceToString(int choice) {
        switch (choice) {
            case ROCK:
                return " ● Rock";
            case PAPER:
                return " □ Paper";
            case SCISSORS:
                return " ▲ Scissors";
            default:
                return "Unknown";
        }
    }
}