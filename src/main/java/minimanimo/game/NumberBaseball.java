package minimanimo.game;

import minimanimo.User;
import java.util.Scanner;

public class NumberBaseball implements MiniGame {
    @Override
    public String getGameName() {
        return "Baseball";
    }

    @Override
    public int startGame(User user, Scanner scanner) {
        System.out.println("Number Baseball Game: Not develpoed yet.");
        return 0; 
    }

    @Override
    public boolean isLowerScoreBetter() {
        return true; 
    }
}