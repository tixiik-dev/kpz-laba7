package lab10.spawn;

import java.util.ArrayList;
import java.util.List;

public class World {
    private final List<Monster> monsters = new ArrayList<>();

    public void add(Monster monster) { monsters.add(monster); }

    public int size() { return monsters.size(); }

    public List<Monster> getMonsters() { return List.copyOf(monsters); }
}
