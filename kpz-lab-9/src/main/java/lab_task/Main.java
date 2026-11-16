package lab_task;

import lab_task.task_1.Equipment;
import lab_task.task_1.Location;
import lab_task.task_1.Monster;
import lab_task.task_2.SpawnSubsystem;
import lab_task.task_2.WeatherSubsystem;
import lab_task.task_3.Arena;
import lab_task.task_3.ArenaPool;
import lab_task.task_3.MatchRequest;
import lab_task.util.EventLogger;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ВАРІАНТ 6: ОНЛАЙН-ГРА «ЗЕМЛІ СІВЕРУ» ===\n");

        runTask1PrototypeDemo();
        System.out.println("\n--------------------------------------------------\n");
        runTask2SingletonDemo();
        System.out.println("\n--------------------------------------------------\n");
        runTask3ObjectPoolDemo();
    }

    public static void runTask1PrototypeDemo() {
        EventLogger.log("Main", "=== ЗАВДАННЯ 1: Спавн монстрів на локаціях (Prototype) ===");

        Monster wolfPrototype = new Monster(
                "Сірий вовк",
                5,
                120,
                new Equipment("Гострі ікла", "Вовча шкура"),
                List.of("Укус", "Ривок"),
                List.of("Шкура вовка", "М'ясо")
        );

        Location blackForest = new Location("Чорний ліс");
        blackForest.spawnFromPrototype(wolfPrototype, 5);

        List<Monster> spawnedWolves = blackForest.getMonsters();
        Monster leaderWolf = spawnedWolves.get(0);
        Monster regularWolf = spawnedWolves.get(1);

        EventLogger.log("Main", "Модифікація першого вовка у ватажка зграї...");
        leaderWolf.promoteToLeader("Прокляті пазурі темряви", 12, "Виття зграї (+20% атаки)");

        System.out.println("\nПорівняння стану об'єктів:");
        System.out.println("ЕТАЛОН:     " + wolfPrototype);
        System.out.println("ВАТАЖОК:    " + leaderWolf);
        System.out.println("ЗВИЧАЙНИЙ:  " + regularWolf);
    }

    public static void runTask2SingletonDemo() {
        EventLogger.log("Main", "=== ЗАВДАННЯ 2: Ігровий час світу (Singleton) ===");

        WeatherSubsystem weather = new WeatherSubsystem();
        SpawnSubsystem spawner = new SpawnSubsystem();

        weather.advanceTimeToNight();
        spawner.updateMonsterSpawns();
    }

    public static void runTask3ObjectPoolDemo() {
        EventLogger.log("Main", "=== ЗАВДАННЯ 3: Розподіл арен для матчів (Object Pool) ===");

        ArenaPool arenaPool = new ArenaPool(3);

        // 5 пар команд одночасно шукають матч
        Arena arena1 = arenaPool.requestMatch(new MatchRequest("Вовки", "Ворони"));
        Arena arena2 = arenaPool.requestMatch(new MatchRequest("Ведмеді", "Рисі"));
        Arena arena3 = arenaPool.requestMatch(new MatchRequest("Соколи", "Зубри"));
        arenaPool.requestMatch(new MatchRequest("Леви", "Кабани"));
        arenaPool.requestMatch(new MatchRequest("Орли", "Тури"));

        // Імітація перебігу першого матчу та його завершення
        EventLogger.log("Матч", "На арені " + arena1.getId() + " триває бій...");
        arena1.simulateGameplay(3, 1);
        Arena nextMatchOnArena1 = arenaPool.releaseArena(arena1);

        // Завершення решти матчів для повної статистики
        arena2.simulateGameplay(2, 2);
        Arena nextMatchOnArena2 = arenaPool.releaseArena(arena2);

        arena3.simulateGameplay(1, 0);
        arenaPool.releaseArena(arena3);

        if (nextMatchOnArena1 != null) {
            nextMatchOnArena1.simulateGameplay(4, 2);
            arenaPool.releaseArena(nextMatchOnArena1);
        }
        if (nextMatchOnArena2 != null) {
            nextMatchOnArena2.simulateGameplay(0, 1);
            arenaPool.releaseArena(nextMatchOnArena2);
        }

        arenaPool.printStatistics();
    }
}