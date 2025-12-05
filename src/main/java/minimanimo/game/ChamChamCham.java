package minimanimo.game;

import minimanimo.User;
import java.util.Scanner;

public class ChamChamCham implements MiniGame {
    @Override
    public String getGameName() {
        return "ChamChamCham";
    }

    @Override
    public int startGame(User user, Scanner scanner) {
        System.out.println("Cham Cham Cham: Not developed yet");
        return 0; 
    }

    @Override
    public boolean isLowerScoreBetter() {
        return false;
    }
}
