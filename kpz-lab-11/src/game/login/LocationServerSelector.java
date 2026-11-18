package game.login;

import game.common.GameLog;

/** Підсистема вибору сервера локації. */
public class LocationServerSelector {
    public String selectServer(PlayerCharacter character) {
        String server = "SRV-" + character.location().replace(' ', '-');
        GameLog.log("LocationServerSelector", "Обрано сервер локації: " + server);
        return server;
    }
}
