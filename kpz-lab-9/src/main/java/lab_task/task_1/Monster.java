package lab_task.task_1;

import lab_task.util.EventLogger;

import java.util.ArrayList;
import java.util.List;

public class Monster implements MonsterPrototype {
    private String species;
    private int level;
    private int health;
    private Equipment equipment;
    private List<String> skills;
    private List<String> lootTable;

    public Monster(String species, int level, int health, Equipment equipment,
                   List<String> skills, List<String> lootTable) {
        EventLogger.simulateHeavyOperation(
                "ФабрикаМонстрів",
                "Завантаження 3D-моделі, анімацій, характеристик та луту для еталона '" + species + "'",
                600
        );
        this.species = species;
        this.level = level;
        this.health = health;
        this.equipment = new Equipment(equipment);
        this.skills = new ArrayList<>(skills);
        this.lootTable = new ArrayList<>(lootTable);
    }

    private Monster(Monster prototype) {
        this.species = prototype.species;
        this.level = prototype.level;
        this.health = prototype.health;
        this.equipment = new Equipment(prototype.equipment);
        this.skills = new ArrayList<>(prototype.skills);
        this.lootTable = new ArrayList<>(prototype.lootTable);
    }

    @Override
    public Monster cloneMonster() {
        return new Monster(this);
    }

    public void promoteToLeader(String newWeapon, int newLevel, String bonusSkill) {
        this.species = this.species + " (Ватажок)";
        this.level = newLevel;
        this.health = (int) (this.health * 1.8);
        this.equipment.setWeapon(newWeapon);
        this.skills.add(bonusSkill);
        this.lootTable.add("Ікло ватажка зграї");
    }

    public String getSpecies() {
        return species;
    }

    @Override
    public String toString() {
        return String.format("%s [Рівень: %d, HP: %d | %s | Уміння: %s | Лут: %s]",
                species, level, health, equipment, skills, lootTable);
    }
}