package minimanimo;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest {

    private UserManager userManager;
    private final String CSV_FILE = "test_users.csv";

    @BeforeEach
    void setUp() throws IOException { // Ensure clean state before each test

        Files.deleteIfExists(Path.of(CSV_FILE));
        userManager = new UserManager(CSV_FILE);
    }

    @AfterEach
    void tearDown() throws IOException { // Clean up after each test
        Files.deleteIfExists(Path.of(CSV_FILE));
    }

    @Test
    void testAddAndGetUser() { // Test adding and retrieving a user
        String nickname = "TestUser";

        userManager.addUser(nickname);
        User retrievedUser = userManager.getUser(nickname);

        assertNotNull(retrievedUser);
        assertEquals(nickname, retrievedUser.getNickname());
    }

    @Test
    void testDataPersistence() { // Test data persistence across UserManager instances
        userManager.addUser("PersistenceUser");

        UserManager newManager = new UserManager(CSV_FILE);
        User loadedUser = newManager.getUser("PersistenceUser");

        assertNotNull(loadedUser);
        assertEquals("PersistenceUser", loadedUser.getNickname());
    }

    @Test
    void testDuplicateUserCheck() { // Test handling of duplicate user addition
        String nickname = "DoubleUser";
        int initialScore = 50;
        userManager.addUser(nickname);
        User firstInstance = userManager.getUser(nickname);
        assertNotNull(firstInstance);
        firstInstance.updateScore("RPS", initialScore);
        userManager.saveUsers();
        assertEquals(1, userManager.getUserCount());
        userManager.addUser(nickname);
        userManager.saveUsers();
        UserManager anotherManager = new UserManager(CSV_FILE);
        User secondInstance = anotherManager.getUser(nickname);
        assertNotNull(secondInstance);
        assertEquals(nickname, secondInstance.getNickname());
        assertEquals(1, anotherManager.getUserCount());
        assertEquals(initialScore, secondInstance.getScore("RPS"));

    }

    @Test
    void testInvalidUser() { // Test handling of invalid user addition
        userManager.addUser("");
        userManager.addUser(null);

        assertNull(userManager.getUser(""));
        assertNull(userManager.getUser(null));
    }

    @Test
    void testCsvFormatIntegrity() { // Test CSV format integrity after saving users
        String nickName = "CsvCheckUser";
        String gameName = "ChamChamCham";
        int score = 10;

        userManager.addUser(nickName);
        User user = userManager.getUser(nickName);
        assertNotNull(user);

        user.updateScore(gameName, score);
        userManager.saveUsers();

        File file = new File(CSV_FILE);
        assertTrue(file.exists());

        UserManager newManager = new UserManager(CSV_FILE);
        User loadedUser = newManager.getUser(nickName);
        assertNotNull(loadedUser);
        assertEquals(nickName, loadedUser.getNickname());
        assertEquals(score, loadedUser.getScore(gameName));

        try {
            long lineCount = Files.lines(Path.of(CSV_FILE)).count();
            assertEquals(2, lineCount);
        } catch (IOException e) {
            fail("IOException occurred while reading the CSV file.");
        }
    }

}