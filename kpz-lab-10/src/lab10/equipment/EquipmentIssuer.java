package lab10.equipment;

import lab10.log.GameLog;
import java.util.Set;

/** Client. Працює лише з абстракціями (EquipmentFactory, Weapon, Armor, Mount). */
public class EquipmentIssuer {
    private static final String SRC = "Видача спорядження";

    public StarterKit issue(String character, EquipmentFactory factory) {
        GameLog.log(SRC, "Персонаж «" + character + "» отримує спорядження, фабрика: " + factory.factionName());
        Weapon weapon = factory.createWeapon();
        Armor armor = factory.createArmor();
        Mount mount = factory.createMount();
        StarterKit kit = new StarterKit(weapon, armor, mount);
        GameLog.log(SRC, "Видано набір -> " + kit);
        Set<String> factions = kit.factions();
        if (factions.size() == 1) {
            GameLog.log(SRC, "Перевірка: усі предмети набору належать до однієї фракції — " + factions.iterator().next());
        } else {
            GameLog.log(SRC, "ПОМИЛКА: набір містить предмети різних фракцій " + factions);
        }
        return kit;
    }
}
