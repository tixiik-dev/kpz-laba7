package game.login;

import game.common.GameLog;

/** Підсистема чату. */
public class ChatService {
    public void connect(String playerName, String serverId) {
        GameLog.log("ChatService", "Гравця " + playerName + " підключено до чату сервера " + serverId);
    }
}
