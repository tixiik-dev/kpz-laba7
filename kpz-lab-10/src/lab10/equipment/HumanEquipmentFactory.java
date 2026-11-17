package lab10.equipment;

import lab10.equipment.*;

/** ConcreteFactory: Люди. */
public class HumanEquipmentFactory implements EquipmentFactory {
    @Override public String factionName() { return "Люди"; }
    @Override public Weapon createWeapon() { return new KnightSword(); }
    @Override public Armor createArmor() { return new Chainmail(); }
    @Override public Mount createMount() { return new WarHorse(); }

    public static class KnightSword extends BaseGear implements Weapon { public KnightSword() { super("Лицарський меч", "Люди"); } }
    public static class Chainmail extends BaseGear implements Armor { public Chainmail() { super("Кольчуга", "Люди"); } }
    public static class WarHorse extends BaseGear implements Mount { public WarHorse() { super("Бойовий кінь", "Люди"); } }
}
