package lab10.equipment;

public abstract class BaseGear implements GearItem {
    private final String name;
    private final String faction;

    protected BaseGear(String name, String faction) {
        this.name = name;
        this.faction = faction;
    }

    @Override public String name() { return name; }
    @Override public String faction() { return faction; }
    @Override public String toString() { return name + " [" + faction + "]"; }
}
