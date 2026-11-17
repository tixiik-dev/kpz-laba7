package lab10;

import lab10.equipment.*;
import lab10.equipment.ext.OrcEquipmentFactory;
import lab10.log.GameLog;
import lab10.spawn.*;
import lab10.spawn.ext.DesertLocation;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

/** Раннер: окремий метод для кожної проблемної ситуації варіанта 6 (завдання 1 і 2). */
public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        banner("Ситуація 1. Спавн монстрів (Factory Method)");
        situation1MonsterSpawn();
        banner("Ситуація 2. Стартове спорядження фракцій (Abstract Factory)");
        situation2StarterEquipment();
    }

    private static void banner(String title) {
        System.out.println();
        System.out.println("===== " + title + " =====");
    }

    static void situation1MonsterSpawn() {
        World world = new World();
        Location[] locations = { new ForestLocation(), new SwampLocation(), new MountainLocation() };
        for (Location l : locations) {
            l.spawn(world);                       // один і той самий процес
        }
        GameLog.log("Сценарій", "Перевірка ліміту: друга спроба спавну на болоті (ліміт 1)");
        locations[1].spawn(world);

        GameLog.log("Сценарій", "Додаємо локацію «пустеля» новими класами, Location.spawn() не змінюється");
        new DesertLocation().spawn(world);
        GameLog.log("Сценарій", "Усього монстрів у світі: " + world.size() + " -> " + world.getMonsters());
    }

    static void situation2StarterEquipment() {
        EquipmentIssuer issuer = new EquipmentIssuer();
        issuer.issue("Лілея", new ElfEquipmentFactory());
        issuer.issue("Торін", new DwarfEquipmentFactory());
        issuer.issue("Богдан", new HumanEquipmentFactory());

        GameLog.log("Сценарій", "Додаємо фракцію орків новими класами, EquipmentIssuer не змінюється");
        issuer.issue("Грош", new OrcEquipmentFactory());
    }
}