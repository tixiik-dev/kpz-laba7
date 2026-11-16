package lab_task.task_1;

import lab_task.util.EventLogger;

import java.util.ArrayList;
import java.util.List;

public class Location {
    private final String name;
    private final List<Monster> monsters = new ArrayList<>();

    public Location(String name) {
        this.name = name;
    }

    public void spawnFromPrototype(Monster prototype, int count) {
        for (int i = 1; i <= count; i++) {
            Monster clone = prototype.cloneMonster();
            monsters.add(clone);
            EventLogger.log("Локація:" + name,
                    "Спавн монстра #" + i + " на основі еталона '" + prototype.getSpecies() + "'");
        }
    }

    public List<Monster> getMonsters() {
        return monsters;
    }
}