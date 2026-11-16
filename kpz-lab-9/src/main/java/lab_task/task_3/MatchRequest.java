package lab_task.task_3;

public class MatchRequest {
    private final String teamA;
    private final String teamB;

    public MatchRequest(String teamA, String teamB) {
        this.teamA = teamA;
        this.teamB = teamB;
    }

    public String getMatchTitle() {
        return teamA + " vs " + teamB;
    }
}