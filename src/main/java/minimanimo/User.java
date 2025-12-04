package minimanimo;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String nickname;
    private Map<String, Integer> scoreMap; 

    public User(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("Nickname cannot be null or empty");
        }
        this.nickname = nickname;
        this.scoreMap = new HashMap<>();
    }

    public String getNickname() {
        return nickname;
    }

    public Map<String, Integer> getScoreMap() {
        return scoreMap;
    }

    public void updateScore(String gameName, int score) {
        if (gameName == null) {
            throw new IllegalArgumentException("Game name cannot be null");
        }
        if (!scoreMap.containsKey(gameName) || scoreMap.get(gameName) < score) {
            scoreMap.put(gameName, score);
        }
    }


    public int getScore(String gameName) {
        if (gameName == null) {
            throw new IllegalArgumentException("Game name cannot be null");
        }
        return scoreMap.getOrDefault(gameName, 0);
        
    }
}
