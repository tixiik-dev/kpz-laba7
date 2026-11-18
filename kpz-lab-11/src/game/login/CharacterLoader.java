package game.login;

import game.common.GameLog;

/** Підсистема завантаження персонажа. */
public class CharacterLoader {
    public PlayerCharacter loadCharacter(String playerName) {
        PlayerCharacter c = new PlayerCharacter(playerName, 12, "Чорний ліс");
        GameLog.log("CharacterLoader", "Завантажено персонажа " + c.name() + " (рівень " + c.level() + ", " + c.location() + ")");
        return c;
    }
}
