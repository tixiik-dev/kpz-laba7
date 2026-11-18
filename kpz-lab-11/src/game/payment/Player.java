package game.payment;

public class Player {
    private final String name;
    private int gameCurrency;

    public Player(String name) {
        this.name = name;
    }

    public String getName() { return name; }

    public int getGameCurrency() { return gameCurrency; }

    public void credit(int amount) { gameCurrency += amount; }
}
