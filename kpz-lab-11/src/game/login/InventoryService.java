package game.login;

import game.common.GameLog;
import java.util.List;

/** Підсистема інвентарю. Доступна і для окремого використання. */
public class InventoryService {
    public List<String> loadInventory(String playerName) {
        List<String> items = List.of("Меч", "Зілля здоров'я", "Щит");
        GameLog.log("InventoryService", "Завантажено інвентар гравця " + playerName + ": " + items.size() + " предм.");
        return items;
    }

    public List<String> reloadInventory(String playerName) {
        GameLog.log("InventoryService", "Перезавантаження інвентарю гравця " + playerName);
        return loadInventory(playerName);
    }
}
