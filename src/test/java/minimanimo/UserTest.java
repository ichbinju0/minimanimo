package minimanimo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

public class UserTest {
    @Test
    void testUserLogic() {
   
        User user = new User("TestUser");
        assertEquals("TestUser", user.getNickname());

        user.updateScore("Baseball", 100);
        assertEquals(100, user.getScore("Baseball"));


        user.updateScore("UpDown", 50);
        assertEquals(50, user.getScore("UpDown"));

        user.updateScore("ChamChamCham", 80);
        assertEquals(80, user.getScore("ChamChamCham"));


        user.updateScore("RockPaperScissors", 30);
        assertEquals(30, user.getScore("RockPaperScissors"));


        assertEquals(100, user.getScore("Baseball"));  
        assertEquals(50, user.getScore("UpDown"));     
        assertEquals(80, user.getScore("ChamChamCham")); 
        assertEquals(30, user.getScore("RockPaperScissors"));
        

        user.updateScore("ChamChamCham", 90);
        assertEquals(90, user.getScore("ChamChamCham"));
    }

    @Test
    void testEdgeCases() {
        User user = new User("EdgeUser");


        int score = user.getScore("GhostGame"); 
        assertEquals(0, score);


        user.updateScore("Baseball", 100); 
        user.updateScore("Baseball", 50);  
        
        assertEquals(100, user.getScore("Baseball")); 
    }
}
