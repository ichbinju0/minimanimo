package minimanimo.game;

import minimanimo.MiniGame;
import minimanimo.User;
import java.util.Random;
import java.util.Scanner;

public class RockPaperScissors implements MiniGame {

    private final Random random = new Random();

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
        System.out.println("Rules: Win (+1 point), Draw (Replay), Lose (Game Over)!");

        int currentScore = 0;
        
        while (true) {
            System.out.println("\n------------------------------");
            System.out.println("Current Streak: " + currentScore);
            System.out.println("What is your move?");
            System.out.print("1.Rock  2.Paper  3.Scissors : ");

            int userChoice;
            
            // Check for non-numeric input and range 1-3
            try {
                String input = scanner.next(); 
                userChoice = Integer.parseInt(input);

                if (userChoice < 1 || userChoice > 3) {
                    System.out.println("⚠️ Invalid input. Please choose 1, 2, or 3.");
                    continue;
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Please enter a number!");
                continue;
            }

            // Logic: 0,1,2 -> Convert to 1,2,3 for consistency
            int computerChoice = random.nextInt(3) + 1;

            printChoices(userChoice, computerChoice);

            // [Game Logic] Determine Winner
            if (userChoice == computerChoice) {
                System.out.println("😐 It's a draw! (Replay round)");
            } else if (isWin(userChoice, computerChoice)) {
                System.out.println("🎉 You won! (+1 point)");
                currentScore++;
            } else {
                System.out.println("😭 You lost... Game Over!");
                break;
            }
        }

        System.out.println("=== Game Over! Final Score: " + currentScore + " ===");
        
        // [Integration] Update user's high score if applicable
        if (user != null) {
            user.updateScore(getGameName(), currentScore);
        }

        return currentScore;
    }

    // Helper method to check if the user won
    private boolean isWin(int user, int computer) {
        // 1:Rock, 2:Paper, 3:Scissors
        // Rock(1) vs Scissors(3) -> Win
        // Paper(2) vs Rock(1)    -> Win
        // Scissors(3) vs Paper(2)-> Win
        return (user == 1 && computer == 3) ||
               (user == 2 && computer == 1) ||
               (user == 3 && computer == 2);
    }

    // Helper method to display choices
    private void printChoices(int user, int computer) {
        System.out.println("Me: " + choiceToString(user) + " VS Computer: " + choiceToString(computer));
    }

    private String choiceToString(int choice) {
        switch (choice) {
            case 1: return "👊 Rock";
            case 2: return "🖐 Paper";
            case 3: return "✌️ Scissors";
            default: return "Unknown";
        }
    }
}
