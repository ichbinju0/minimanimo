package minimanimo.game;

import java.util.Random;
import java.util.Scanner;

import minimanimo.User;

public class ChamChamCham implements MiniGame {
    @Override
    public String getGameName() {
        return "ChamChamCham";
    }


    @Override
    public boolean isLowerScoreBetter() {
        return false;
    }

    @Override
    public int startGame(User user, Scanner scanner) {
        System.out.println("==========================================");
        System.out.println("       Welcome to Cham-Cham-Cham!         ");
        System.out.println("==========================================");
        System.out.println("Rule: If you look in the DIFFERENT direction from the computer, you WIN!");
        System.out.println("Tip: You can press '0' at any time to quit and save your score.");

        int score = 0;
        Random random = new Random();
        String[] directions = {"L", "C", "R"};

        while (true) {
            System.out.println("\n------------------------------------------");
            // Quit option added
            System.out.print("Select Direction (L: Left / C: Center / R: Right) or '0' to Quit: ");
            
            String input = scanner.nextLine().trim().toUpperCase();

            // Handle empty inputS
            if (input.isEmpty()) {
                System.out.println("Input cannot be empty. Please choose L, C, R, or 0.");
                continue; 
            }

            // Quit condition
            if (input.equals("0")) {
                System.out.println(" Game Stopped by User.");
                System.out.println("Final Score: " + score);
                break; // game ends here
            }

            // Input validation
            if (!input.equals("L") && !input.equals("C") && !input.equals("R")) {
                System.out.println("[ERROR] Invalid input! Please enter L, C, R, or 0.");
                continue;
            }

            String computerDir = directions[random.nextInt(3)];

            System.out.println("You: " + getFullDirectionName(input) 
                    + "  VS  Computer: " + getFullDirectionName(computerDir));

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Determine win/loss
            if (input.equals(computerDir)) {
                System.out.println(" MATCH... You lost.");
                System.out.println("Game Over! Your Final Score: " + score);
                break;
            } else {
                System.out.println(" No MATCH! You won this round!");
                score++;
                System.out.println("Current Winstreak: " + score);
            }
        }
        
        return score; // return final score
    }

    // Helper method to convert direction code to full name
    private String getFullDirectionName(String code) {
        switch (code) {
            case "L": return "LEFT";
            case "C": return "CENTER";
            case "R": return "RIGHT";
            default: return "UNKNOWN";
        }
    }
}
    
