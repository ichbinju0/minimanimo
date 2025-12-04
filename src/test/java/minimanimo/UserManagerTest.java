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

        UserManager newManager = new UserManager();
        User loadedUser = newManager.getUser("PersistenceUser");

        assertNotNull(loadedUser);
        assertEquals("PersistenceUser", loadedUser.getNickname());
    }

    @Test
    void testDuplicateUserCheck() { // Test handling of duplicate user addition
        String nickname = "DoubleUser";
        userManager.addUser(nickname);

        userManager.addUser(nickname);

        assertNotNull(userManager.getUser(nickname));
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
        userManager.addUser("CsvCheck");
        User user = userManager.getUser("CsvCheck");
        user.updateScore("ChamChamCham", 10);
        userManager.saveUsers();

        File file = new File(CSV_FILE);
        assertTrue(file.exists());
    }
}
