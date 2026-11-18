package game.login;

public record LoginResult(String playerName, String sessionId, String serverId, int itemsCount, int bonus) { }
