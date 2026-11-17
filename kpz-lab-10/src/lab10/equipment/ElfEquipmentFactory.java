package lab10.equipment;

import lab10.equipment.*;

/** ConcreteFactory: Ельфи. */
public class ElfEquipmentFactory implements EquipmentFactory {
    @Override public String factionName() { return "Ельфи"; }
    @Override public Weapon createWeapon() { return new ElvenBow(); }
    @Override public Armor createArmor() { return new ElvenCloak(); }
    @Override public Mount createMount() { return new Stag(); }

    public static class ElvenBow extends BaseGear implements Weapon { public ElvenBow() { super("Ельфійський лук", "Ельфи"); } }
    public static class ElvenCloak extends BaseGear implements Armor { public ElvenCloak() { super("Плащ із ельфійського шовку", "Ельфи"); } }
    public static class Stag extends BaseGear implements Mount { public Stag() { super("Білий олень", "Ельфи"); } }
}
