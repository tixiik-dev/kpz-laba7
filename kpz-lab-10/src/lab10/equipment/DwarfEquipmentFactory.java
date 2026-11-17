package lab10.equipment;

import lab10.equipment.*;

/** ConcreteFactory: Гноми. */
public class DwarfEquipmentFactory implements EquipmentFactory {
    @Override public String factionName() { return "Гньоми"; }
    @Override public Weapon createWeapon() { return new DwarvenAxe(); }
    @Override public Armor createArmor() { return new DwarvenPlate(); }
    @Override public Mount createMount() { return new MountainGoat(); }

    public static class DwarvenAxe extends BaseGear implements Weapon { public DwarvenAxe() { super("Гномівська сокира", "Гноми"); } }
    public static class DwarvenPlate extends BaseGear implements Armor { public DwarvenPlate() { super("Гномівські латні обладунки", "Гноми"); } }
    public static class MountainGoat extends BaseGear implements Mount { public MountainGoat() { super("Гірський цап", "Гноми"); } }
}
