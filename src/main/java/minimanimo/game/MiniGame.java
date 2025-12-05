package minimanimo;

import java.util.Scanner;

public interface MiniGame {

    
    //Returns the display name of the game.
    String getGameName();


    //Starts the game for a given user and returns the score.
    int startGame(User user, Scanner scanner);


    //Indicates whether a lower score means a better performance.
    boolean isLowerScoreBetter();
}