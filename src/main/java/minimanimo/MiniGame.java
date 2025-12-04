package minimanimo;


public interface MiniGame {
    String getGameName();

    int startGame(User user, java.util.Scanner scanner);

    boolean isLowerScoreBetter();
}