package minimanimo.game;

import minimanimo.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class NumberBaseballTest {

    @Test
    @DisplayName("Verify Game Metadata (Name & Ranking Criteria)")
    void testGameMetadata() {
        NumberBaseball game = new NumberBaseball();
        
        // 1. Verify Game Name
        assertEquals("Baseball", game.getGameName(), "Game name should be 'Baseball'.");
        
        // 2. Verify Ranking Criteria
        // Score = 10 - attempts. Higher is better. So isLowerScoreBetter() -> false.
        assertFalse(game.isLowerScoreBetter(), "Number Baseball uses (10 - Attempts) scoring, so higher is better (false).");
    }

    @Test
    @DisplayName("Test Game Execution - Full Game Loop")
    void testGameExecution() {
        // [Test Scenario]
        // Since the secret number is random, we cannot guarantee a win.
        // Instead, we simulate a 'Game Over' scenario by providing 10 incorrect inputs.
        // This ensures the game loop runs 10 times and exits gracefully when life hits 0.
        
        // Prepare 10 valid guesses (3 digits, no duplicates) to consume all lives.
        String simulatedInput = 
            "123\n" + 
            "456\n" + 
            "789\n" + 
            "147\n" + 
            "258\n" + 
            "369\n" + 
            "159\n" + 
            "753\n" + 
            "951\n" + 
            "357\n"; // 10th attempt

        InputStream in = new ByteArrayInputStream(simulatedInput.getBytes());
        Scanner scanner = new Scanner(in);
        
        User user = new User("TestTester");
        NumberBaseball game = new NumberBaseball();

        // Run the game.
        // It should either return a score (if lucky win) or 0 (game over).
        // Crucially, it must NOT throw NoSuchElementException (run out of input).
        assertDoesNotThrow(() -> {
            int score = game.startGame(user, scanner);
            System.out.println("Test finished with score: " + score);
        });
    }
}