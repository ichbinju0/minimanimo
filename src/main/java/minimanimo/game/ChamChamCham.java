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
        System.out.println("\n\n");
        System.out.println("##########################################");
        System.out.println("##                                      ##");
        System.out.println("##          CHAM - CHAM - CHAM          ##");
        System.out.println("##                                      ##");
        System.out.println("##########################################");
        System.out.println("\n[RULE] Look in a DIFFERENT direction to WIN!");
        System.out.println("[TIP]  Enter '0' to save & quit.\n");

        int score = 0;
        Random random = new Random();
        String[] directions = { "L", "C", "R" };

        while (true) {
            System.out.println("------------------------------------------");
            System.out.println("Which way will you look?");
            System.out.println("[ L: << Left / C: ^^ Center / R: >> Right ]  (0: Quit)");
            System.out.print(">> ");
            String input = scanner.nextLine().trim().toUpperCase();

            // Handle empty inputS
            if (input.isEmpty()) {
                System.out.println("[ERROR] Input cannot be empty. Please choose L, C, R, or 0.");
                continue;
            }

            // Quit condition
            if (input.equals("0")) {
                System.out.println(" Game Stopped by User.");
                System.out.println("=== Game Over! Final Score: " + score + " ===");
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

            System.out.print("\nCham... ");
            try {
                Thread.sleep(300);
                System.out.print("Cham... ");
                Thread.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Cham!!\n");

            System.out.println(String.format("   You  %s   VS   %s  Computer",
                    getFullDirectionName(input), getFullDirectionName(computerDir)));
            System.out.println("");

            // Determine win/loss
            if (input.equals(computerDir)) {
                System.out.println("(>_<) HIT! You looked the same way...");
                System.out.println("===========================================");
                System.out.println("   [X] GAME OVER! Final Score: " + score);
                System.out.println("===========================================");
                break;
            } else {
                System.out.println("(^_^) DODGE SUCCESS! (+1 Point)");
                score++;
                System.out.println("   [ Current Winstreak: " + score + " ]");
            }
        }

        return score; // return final score
    }

    // Helper method to convert direction code to full name
    private String getFullDirectionName(String code) {
        switch (code) {
            case "L":
                return "<< LEFT";
            case "C":
                return "^^ CENTER";
            case "R":
                return ">> RIGHT";
            default:
                return "UNKNOWN";
        }
    }
}
