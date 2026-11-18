package game.login;

import game.common.GameLog;
import java.util.List;

/**
 * Facade: єдине місце, де описана послідовність входу у світ.
 * Клієнти (LoginHandler, ReconnectHandler) викликають лише enterWorld().
 */
public class WorldLoginFacade {
    private final AuthenticationService authentication;
    private final CharacterLoader characterLoader;
    private final InventoryService inventory;
    private final LocationServerSelector serverSelector;
    private final ChatService chat;
    private final DailyBonusService dailyBonus;

    public WorldLoginFacade(AuthenticationService authentication, CharacterLoader characterLoader,
                            InventoryService inventory, LocationServerSelector serverSelector,
                            ChatService chat, DailyBonusService dailyBonus) {
        this.authentication = authentication;
        this.characterLoader = characterLoader;
        this.inventory = inventory;
        this.serverSelector = serverSelector;
        this.chat = chat;
        this.dailyBonus = dailyBonus;
    }

    public LoginResult enterWorld(String playerName, String token) {
        GameLog.log("WorldLoginFacade", "Починаю послідовність входу для " + playerName);
        String session = authentication.authenticate(playerName, token);
        PlayerCharacter character = characterLoader.loadCharacter(playerName);
        List<String> items = inventory.loadInventory(playerName);
        String server = serverSelector.selectServer(character);
        chat.connect(playerName, server);
        int bonus = dailyBonus.grantDailyBonus(playerName);
        GameLog.log("WorldLoginFacade", "Вхід гравця " + playerName + " завершено");
        return new LoginResult(playerName, session, server, items.size(), bonus);
    }
}
