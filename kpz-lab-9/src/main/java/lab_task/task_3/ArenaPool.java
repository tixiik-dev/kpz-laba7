package lab_task.task_3;

import lab_task.util.EventLogger;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

public class ArenaPool {
    private final int maxArenas;
    private final List<Arena> allArenas = new ArrayList<>();
    private final Queue<Arena> availableArenas = new ArrayDeque<>();
    private final Queue<MatchRequest> waitingQueue = new ArrayDeque<>();

    public ArenaPool(int maxArenas) {
        this.maxArenas = maxArenas;
    }

    public Arena requestMatch(MatchRequest request) {
        Arena arena = acquireArena();
        if (arena != null) {
            arena.startMatch(request.getMatchTitle());
            EventLogger.log("ПулАрен", "Арена " + arena.getId() + " -> матч " + arena.getCurrentMatch()
                    + " (початковий рахунок " + arena.getScore() + ")");
            return arena;
        } else {
            waitingQueue.offer(request);
            EventLogger.log("Матчмейкінг", "Вільних арен немає. Пара '" + request.getMatchTitle()
                    + "' очікує у черзі (позиція в черзі: " + waitingQueue.size() + ")");
            return null;
        }
    }

    private Arena acquireArena() {
        if (!availableArenas.isEmpty()) {
            return availableArenas.poll();
        }
        if (allArenas.size() < maxArenas) {
            String arenaId = "А-" + (allArenas.size() + 1);
            Arena newArena = new Arena(arenaId);
            allArenas.add(newArena);
            return newArena;
        }
        return null;
    }

    public Arena releaseArena(Arena arena) {
        if (arena == null) {
            return null;
        }
        String finishedMatch = arena.getCurrentMatch();
        arena.resetState();
        EventLogger.log("ПулАрен", arena.getId() + " повернено після матчу '" + finishedMatch
                + "', стан скинуто (рахунок " + arena.getScore() + ")");

        MatchRequest nextRequest = waitingQueue.poll();
        if (nextRequest != null) {
            arena.startMatch(nextRequest.getMatchTitle());
            EventLogger.log("ПулАрен", "Арена " + arena.getId() + " -> матч із черги "
                    + arena.getCurrentMatch() + " (рахунок нового матчу " + arena.getScore() + ")");
            return arena;
        } else {
            availableArenas.offer(arena);
            return null;
        }
    }

    public void printStatistics() {
        System.out.println("\nСтатистика використання арен:");
        for (Arena arena : allArenas) {
            System.out.printf("- Арена %s: проведено матчів = %d%n",
                    arena.getId(), arena.getMatchesHosted());
        }
        System.out.println("Загальна кількість створених арен: " + allArenas.size());
    }
}