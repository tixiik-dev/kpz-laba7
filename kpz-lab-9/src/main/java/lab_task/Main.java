package lab_task;

import lab_task.task_1.Equipment;
import lab_task.task_1.Location;
import lab_task.task_1.Monster;
import lab_task.task_2.SpawnSubsystem;
import lab_task.task_2.WeatherSubsystem;
import lab_task.util.EventLogger;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== ВАРІАНТ 6: ОНЛАЙН-ГРА «ЗЕМЛІ СІВЕРУ» ===\n");

        runTask1PrototypeDemo();
        System.out.println("\n--------------------------------------------------\n");
        runTask2SingletonDemo();
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
}