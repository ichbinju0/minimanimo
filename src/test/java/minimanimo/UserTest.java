package minimanimo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    void testUserLogic() {
        //Test User Creation
        User user = new User("TestUser");
        assertEquals("TestUser", user.getNickname());
        //Test Score Updates
        user.updateScore("Baseball", 100);
        assertEquals(100, user.getScore("Baseball"));


        user.updateScore("UpDown", 50);
        assertEquals(50, user.getScore("UpDown"));

        user.updateScore("ChamChamCham", 80);
        assertEquals(80, user.getScore("ChamChamCham"));


        user.updateScore("RPS", 30);
        assertEquals(30, user.getScore("RPS"));

        //Test Multiple Score Updates
        assertEquals(100, user.getScore("Baseball"));  
        assertEquals(50, user.getScore("UpDown"));     
        assertEquals(80, user.getScore("ChamChamCham")); 
        assertEquals(30, user.getScore("RPS"));
        
        //Test Score Improvement
        user.updateScore("ChamChamCham", 90);
        assertEquals(90, user.getScore("ChamChamCham"));
    }

    @Test
    void testEdgeCases() {
        User user = new User("EdgeUser");

        // Quering a game that has never been played
        int score = user.getScore("GhostGame"); 
        assertEquals(0, score);

        // Score defense scenario
        user.updateScore("Baseball", 100); 
        user.updateScore("Baseball", 50);  
        
        assertEquals(100, user.getScore("Baseball")); 
    }

    @Test
    void testValidation() {
        // Test invalid nickname
        assertThrows(IllegalArgumentException.class, () -> new User(null));

        assertThrows(IllegalArgumentException.class, () -> new User(""));

        assertThrows(IllegalArgumentException.class, () -> new User("   "));

        // Test invalid game names
        User user = new User("ValidUser");
        assertThrows(IllegalArgumentException.class, () -> user.getScore(null));
        assertThrows(IllegalArgumentException.class, () -> user.updateScore(null, 100));
    }
}
