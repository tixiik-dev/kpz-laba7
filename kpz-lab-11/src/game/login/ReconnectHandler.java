package game.login;

import game.common.GameLog;

/** Клієнт фасаду: вхід після розриву з'єднання. З підсистемами напряму не працює. */
public class ReconnectHandler {
    private final WorldLoginFacade facade;

    public ReconnectHandler(WorldLoginFacade facade) {
        this.facade = facade;
    }

    public LoginResult handleReconnect(String playerName, String token) {
        GameLog.log("ReconnectHandler", "З'єднання відновлено: перепідключення гравця " + playerName);
        return facade.enterWorld(playerName, token);
    }
}
