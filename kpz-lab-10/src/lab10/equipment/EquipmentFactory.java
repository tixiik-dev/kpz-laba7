package lab10.equipment;

/** AbstractFactory: одна фабрика = одна фракція = узгоджена родина продуктів. */
public interface EquipmentFactory {
    String factionName();
    Weapon createWeapon();
    Armor createArmor();
    Mount createMount();
}
