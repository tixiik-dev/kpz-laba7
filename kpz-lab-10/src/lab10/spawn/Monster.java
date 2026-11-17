package lab10.spawn;

/** Product. */
public abstract class Monster {
    private final String name;
    private final int health;
    private final int attack;

    protected Monster(String name, int health, int attack) {
        this.name = name;
        this.health = health;
        this.attack = attack;
    }

    public String getName() { return name; }

    @Override
    public String toString() {
        return name + " (HP " + health + ", атака " + attack + ")";
    }
}
