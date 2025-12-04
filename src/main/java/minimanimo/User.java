package minimanimo;

import java.util.HashMap;
import java.util.Map;

public class User {
    private String nickname;
    private Map<String, Integer> scoreMap; 

    public User(String nickname) {
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
        if (!scoreMap.containsKey(gameName) || scoreMap.get(gameName) < score) {
            scoreMap.put(gameName, score);
        }
    }


    public int getTotalScore() {
        int total = 0;
        for (int score : scoreMap.values()) {
            total += score;
        }
        return total;
    }
}
