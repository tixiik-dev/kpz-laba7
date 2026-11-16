package lab_task.task_3;

import lab_task.util.EventLogger;

import java.util.ArrayList;
import java.util.List;

public class Arena {
    private final String id;
    private String currentMatch;
    private int teamAScore;
    private int teamBScore;
    private int timerSeconds;
    private final List<String> activeItems;
    private int matchesHosted;

    public Arena(String id) {
        this.id = id;
        EventLogger.simulateHeavyOperation(
                "СерверАрен",
                "Завантаження мапи, розставлення об'єктів та налаштування таймера для арени " + id,
                500
        );
        this.activeItems = new ArrayList<>();
        this.matchesHosted = 0;
        resetState();
    }

    public void startMatch(String matchTitle) {
        this.currentMatch = matchTitle;
        this.teamAScore = 0;
        this.teamBScore = 0;
        this.timerSeconds = 900; // 15 хвилин на матч
        this.activeItems.add("Руна посилення");
        this.activeItems.add("Зілля здоров'я");
        this.matchesHosted++;
    }

    public void simulateGameplay(int scoreA, int scoreB) {
        this.teamAScore = scoreA;
        this.teamBScore = scoreB;
        this.timerSeconds = 120;
        this.activeItems.add("Трофейний прапор");
    }

    public void resetState() {
        this.currentMatch = null;
        this.teamAScore = 0;
        this.teamBScore = 0;
        this.timerSeconds = 0;
        this.activeItems.clear();
    }

    public String getId() {
        return id;
    }

    public String getCurrentMatch() {
        return currentMatch;
    }

    public String getScore() {
        return teamAScore + ":" + teamBScore;
    }

    public int getMatchesHosted() {
        return matchesHosted;
    }
}