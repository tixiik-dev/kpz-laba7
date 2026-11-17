package lab10.spawn;

import lab10.log.GameLog;
import java.util.Random;


public abstract class Location {
    private final String name;
    private final int monsterLimit;
    private int spawned = 0;
    private final Random random;

    protected Location(String name, int monsterLimit) {
        this.name = name;
        this.monsterLimit = monsterLimit;
        this.random = new Random(name.hashCode());
    }

    public String getName() { return name; }


    public final void spawn(World world) {
        String src = "Спавн/" + name;
        GameLog.log(src, "Початок спавну");
        if (spawned >= monsterLimit) {
            GameLog.log(src, "Ліміт монстрів досягнуто (" + spawned + "/" + monsterLimit + "), спавн скасовано");
            return;
        }
        String point = pickSpawnPoint();
        GameLog.log(src, "Обрано точку появи " + point);
        Monster monster = createMonster();          // фабричний метод
        GameLog.log(src, "Створено монстра: " + monster);
        world.add(monster);
        spawned++;
        GameLog.log(src, "Монстра додано у світ (" + spawned + "/" + monsterLimit + ")");
    }

    private String pickSpawnPoint() {
        return "(" + random.nextInt(100) + ", " + random.nextInt(100) + ")";
    }

    /** Factory Method. */
    protected abstract Monster createMonster();
}
