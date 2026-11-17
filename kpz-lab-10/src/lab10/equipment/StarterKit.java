package lab10.equipment;

import java.util.LinkedHashSet;
import java.util.Set;

public final class StarterKit {
    private final Weapon weapon;
    private final Armor armor;
    private final Mount mount;

    public StarterKit(Weapon weapon, Armor armor, Mount mount) {
        this.weapon = weapon;
        this.armor = armor;
        this.mount = mount;
    }

    public Set<String> factions() {
        Set<String> result = new LinkedHashSet<>();
        result.add(weapon.faction());
        result.add(armor.faction());
        result.add(mount.faction());
        return result;
    }

    @Override
    public String toString() {
        return "зброя: " + weapon + "; броня: " + armor + "; їздова тварина: " + mount;
    }
}
