package minimanimo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import static org.junit.jupiter.api.Assertions.*;

class GameLauncherTest {

    private final String REAL_DATA_FILE = "users.csv";
    private final String BACKUP_DATA_FILE = "users_backup_for_test.csv";
    
    // Stream to capture console output
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();
    private final PrintStream standardOut = System.out;
    private final InputStream standardIn = System.in;

    @BeforeEach
    void setUp() throws IOException {
        // 1. Protect original data: Rename existing users.csv to a backup file
        File originalFile = new File(REAL_DATA_FILE);
        if (originalFile.exists()) {
            Files.move(originalFile.toPath(), Path.of(BACKUP_DATA_FILE), StandardCopyOption.REPLACE_EXISTING);
        }

        // 2. Start capturing console output (Redirect System.out for analysis)
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() throws IOException {
        // 1. Restore standard I/O streams
        System.setOut(standardOut);
        System.setIn(standardIn);

        // 2. Delete the dummy users.csv created during the test
        Files.deleteIfExists(Path.of(REAL_DATA_FILE));

        // 3. Restore original data: Rename the backup file back to the original name
        File backupFile = new File(BACKUP_DATA_FILE);
        if (backupFile.exists()) {
            Files.move(backupFile.toPath(), Path.of(REAL_DATA_FILE), StandardCopyOption.REPLACE_EXISTING);
        }
    }

    /**
     * Helper method: Set up simulated keyboard input
     */
    private void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }

    @Test
    void testRegisterAndPlayUpDown() {
        // Scenario:
        // 1. Select '2' (Register) from the menu
        // 2. Enter nickname 'Tester01'
        // 3. Login successful -> Select '4' (Up Down Game) from main menu
        // 4. Game runs -> Select '0' (Exit) from main menu
        String scenario = "2\nTester01\n4\n0\n";
        provideInput(scenario);

        // Execute GameLauncher
        GameLauncher.main(new String[]{});

        // Verify results (Check the string printed to the console)
        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Registration successful"), "Registration success message should appear.");
        assertTrue(output.contains("Welcome, Tester01"), "Welcome message should include the nickname.");
        assertTrue(output.contains("Number Up Down game"), "Up Down game should be executed."); // Check UpDown class output
        assertTrue(output.contains("Goodbye"), "Application should exit normally.");
    }

    @Test
    void testLoginFailureAndRetry() {
        // Scenario:
        // 1. Select '1' (Login)
        // 2. Enter non-existent ID 'GhostUser' -> Fail
        // 3. Select '2' (Register) when menu reappears
        // 4. Register as 'RealUser'
        // 5. Select '0' (Exit)
        String scenario = "1\nGhostUser\n2\nRealUser\n0\n";
        provideInput(scenario);

        GameLauncher.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("User not found"), "Error message should appear when logging in with a non-existent user.");
        assertTrue(output.contains("Login Successful") || output.contains("Registration successful"), "Login should succeed after retry/registration.");
    }

    @Test
    void testInvalidMenuInput() {
        // Scenario:
        // 1. Enter invalid character 'abc' at initial menu
        // 2. Enter '2' to register ('ValidUser')
        // 3. Enter invalid number '99' at game menu
        // 4. Enter '0' to exit
        String scenario = "abc\n2\nValidUser\n99\n0\n";
        provideInput(scenario);

        GameLauncher.main(new String[]{});

        String output = outputStreamCaptor.toString();

        assertTrue(output.contains("Invalid"), "Warning message should appear for invalid input.");
        assertTrue(output.contains("Please select a game"), "Menu should reappear after invalid input.");
    }
    
    @Test
    void testDataSaveCheck() {
        // Scenario: Register and exit immediately to check if file is created
        String scenario = "2\nSaveTester\n0\n";
        provideInput(scenario);
        
        GameLauncher.main(new String[]{});
        
        // Verify if users.csv is actually created during the test
        File file = new File(REAL_DATA_FILE);
        assertTrue(file.exists(), "users.csv file should be created after execution.");
        assertTrue(file.length() > 0, "File should contain data.");
    }
}
