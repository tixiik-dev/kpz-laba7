package game.login;

import game.common.GameLog;

/** Клієнт фасаду: звичайний вхід. З підсистемами напряму не працює. */
public class LoginHandler {
    private final WorldLoginFacade facade;

    public LoginHandler(WorldLoginFacade facade) {
        this.facade = facade;
    }

    public LoginResult handleLogin(String playerName, String token) {
        GameLog.log("LoginHandler", "Звичайний вхід: гравець " + playerName);
        return facade.enterWorld(playerName, token);
    }
}
