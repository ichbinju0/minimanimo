package minimanimo.game;

import minimanimo.User;
import java.util.Scanner;

public class RockPaperScissors implements MiniGame {
    @Override
    public String getGameName() {
        return "RPS";
    }

    @Override
    public int startGame(User user, Scanner scanner) {
        System.out.println("Rock Paper Scissors: Not develpoed yet.");
        return 0; 
    }

    @Override
    public boolean isLowerScoreBetter() {
        return false; 
    }
}