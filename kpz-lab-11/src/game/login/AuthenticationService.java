package game.login;

import game.common.GameLog;

/** Підсистема автентифікації. */
public class AuthenticationService {
    public String authenticate(String playerName, String token) {
        GameLog.log("AuthenticationService", "Перевірка токена гравця " + playerName + " -> успішно");
        return "SES-" + playerName.toUpperCase();
    }
}
