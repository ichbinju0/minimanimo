package minimanimo.game;

import minimanimo.User;
import java.util.Scanner;

public class UpDown implements MiniGame {
    @Override
    public String getGameName() {
        return "UpDown";
    }

    @Override
    public int startGame(User user, Scanner scanner) {
        System.out.println("Number Up Down game: Not developed yet");
        return 0;
    }

    @Override
    public boolean isLowerScoreBetter() {
        return true; 
    }
}
