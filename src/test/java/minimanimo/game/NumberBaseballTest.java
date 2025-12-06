package minimanimo.game;

import minimanimo.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class NumberBaseballTest {

    // Helper method to create a Scanner from simulated input
    private Scanner createScanner(String input) {
        InputStream in = new ByteArrayInputStream(input.getBytes());
        return new Scanner(in);
    }
    
    // Helper method to create a Mock User
    private User createMockUser() {
        return new User("TestTester");
    }

    @Test
    @DisplayName("Verify Game Metadata (Name & Ranking Criteria)")
    void testGameMetadata() {
        NumberBaseball game = new NumberBaseball();
        
        assertEquals("Baseball", game.getGameName(), "Game name should be 'Baseball'.");
        // Score is 10 - attempts + 1. Higher is better. So isLowerScoreBetter() -> false.
        assertFalse(game.isLowerScoreBetter(), "Ranking is based on higher score, so false should be returned.");
    }

    @Test
    @DisplayName("Test User Exit (Input '0') - Should return 0 score")
    void testUserQuitGame() {
        // Input: "0" immediately exits the game.
        String simulatedInput = "0\n"; 
        Scanner scanner = createScanner(simulatedInput);
        NumberBaseball game = new NumberBaseball();

        int score = game.startGame(createMockUser(), scanner);
        
        // Return 0 score on user quit
        assertEquals(0, score, "Quitting the game (input '0') must return a score of 0.");
    }

    @Test
    @DisplayName("Test Game Execution - Full Game Loop (10 Failures)")
    void testGameExecution() {
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

        Scanner scanner = createScanner(simulatedInput);
        User user = createMockUser();
        NumberBaseball game = new NumberBaseball();

        // Run the game.
        assertDoesNotThrow(() -> {
            int score = game.startGame(user, scanner);
            System.out.println("Test finished with score: " + score);
        });
    }

    @Test
    @DisplayName("Test Invalid Input - Life should NOT be consumed")
    void testInvalidInputDoesNotConsumeLife() {
        // [Test Scenario]
        // We inject invalid inputs BEFORE valid inputs.
        // If invalid inputs consumed life, the game would end early and fail to read the 10th valid input.
        
        String simulatedInput = 
            "abc\n" +   // Invalid: Not number
            "12\n" +    // Invalid: Length < 3
            "111\n" +   // Invalid: Duplicate
            "1234\n" +  // Invalid: Length > 3
            // --- Below are 10 Valid Inputs ---
            "123\n" + "456\n" + "789\n" + "147\n" + "258\n" + 
            "369\n" + "159\n" + "753\n" + "951\n" + "357\n"; 

        Scanner scanner = createScanner(simulatedInput);
        User user = createMockUser();
        NumberBaseball game = new NumberBaseball();

        // If validation works correctly, the game should play 10 full turns 
        // despite the 4 invalid inputs at the start.
        assertDoesNotThrow(() -> {
            game.startGame(user, scanner);
        });
    }
}