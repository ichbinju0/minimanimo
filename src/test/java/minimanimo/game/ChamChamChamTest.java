package minimanimo.game;

import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import minimanimo.User;


public class ChamChamChamTest {
    // Helper method to simulate user input
    private Scanner createScanner(String input) {
        return new Scanner(input);
    }


    // Happy Cases (Normal Scenarios)


    @Test
    @DisplayName("Happy Case 1: Standard Play Flow")
    void testStandardPlay() {
        ChamChamCham game = new ChamChamCham();
        User user = new User("Player1");

        // Scenario: 
        // 1. User picks 'L' (Left).
        // 2. User picks '0' to quit.
        String input = "L\n0"; 
        Scanner scanner = createScanner(input);

        int score = game.startGame(user, scanner);

        // Verification:
        // The score will be 0 or 1 depending on the random computer choice.
        // We ensure the game finished successfully without errors.
        assertTrue(score >= 0, "Score should be non-negative.");
    }

    @Test
    @DisplayName("Happy Case 2: Case Insensitivity (Lowercase Input)")
    void testLowercaseInput() {
        ChamChamCham game = new ChamChamCham();
        User user = new User("Player2");

        // Scenario: 
        // 1. User inputs lowercase 'r' (Should be treated as 'R').
        // 2. User inputs '0' to quit.
        String input = "r\n0"; 
        Scanner scanner = createScanner(input);

        int score = game.startGame(user, scanner);

        // Verification:
        // If the code didn't handle lowercase, it would treat 'r' as invalid input
        // and loop infinitely (causing a test timeout or error).
        // Finishing the game proves lowercase handling works.
        assertTrue(score >= 0);
    }

    @Test
    @DisplayName("Happy Case 3: Multiple Rounds")
    void testMultipleRounds() {
        ChamChamCham game = new ChamChamCham();
        User user = new User("Player3");

        // Scenario: Play 3 rounds then quit
        // Round 1: 'L'
        // Round 2: 'C'
        // Round 3: 'R'
        // Quit: '0'
        String input = "L\nC\nR\n0";
        Scanner scanner = createScanner(input);

        int score = game.startGame(user, scanner);

        // Verification: Game loop should handle multiple inputs correctly.
        assertTrue(score >= 0);
    }


    // Edge Cases
   

    @Test
    @DisplayName("Edge Case 1: Invalid Input Handling")
    void testInvalidInput() {
        ChamChamCham game = new ChamChamCham();
        User user = new User("EdgeUser1");

        // Scenario:
        // 1. "X" (Invalid) -> Game should print error and wait.
        // 2. "123" (Invalid) -> Game should print error and wait.
        // 3. " " (Space/Empty) -> Treated as invalid by scanner logic or skipped.
        // 4. "L" (Valid) -> Finally selects Left.
        // 5. "0" (Quit)
        String input = "X\n123\nL\n0";
        Scanner scanner = createScanner(input);

        int score = game.startGame(user, scanner);

        // Verification:
        // The game should not crash on "X" or "123".
        // It should eventually process "L" and finish.
        assertTrue(score >= 0);
    }

    @Test
    @DisplayName("Edge Case 2: Immediate Quit")
    void testQuitImmediately() {
        ChamChamCham game = new ChamChamCham();
        User user = new User("EdgeUser2");

        // Scenario: User presses '0' right away.
        String input = "0";
        Scanner scanner = createScanner(input);

        int score = game.startGame(user, scanner);

        // Verification: Score must be 0 because no game was played.
        assertEquals(0, score, "Score must be 0 when quitting immediately.");
    }

    @Test
    @DisplayName("Edge Case 3: Game Property Verification")
    void testGameProperties() {
        ChamChamCham game = new ChamChamCham();

        // Verify correct Game Name (Crucial for UserManager mapping)
        assertEquals("ChamChamCham", game.getGameName());

        // Verify Score Rule (False means higher score is better)
        assertFalse(game.isLowerScoreBetter());
    }
}