package game;

import game.common.GameLog;
import game.login.*;
import game.payment.*;
import java.math.BigDecimal;

/** Клас-раннер: окремий метод для кожної ситуації. */
public class Main {

    public static void main(String[] args) {
        situation1Login();
        situation2Payment();
    }

    // Завдання 1 — Facade
    static void situation1Login() {
        GameLog.section("Ситуація 1: вхід гравця у світ (Facade)");
        InventoryService inventory = new InventoryService();
        WorldLoginFacade facade = new WorldLoginFacade(new AuthenticationService(), new CharacterLoader(),
                inventory, new LocationServerSelector(), new ChatService(), new DailyBonusService());
        LoginHandler loginHandler = new LoginHandler(facade);
        ReconnectHandler reconnectHandler = new ReconnectHandler(facade);

        System.out.println("--- Крок 1: звичайний вхід однією операцією ---");
        loginHandler.handleLogin("Svitoyar", "token-1");

        System.out.println("--- Крок 2: перепідключення після розриву тією самою операцією ---");
        reconnectHandler.handleReconnect("Svitoyar", "token-2");

        System.out.println("--- Крок 3: окремо перезавантажити інвентар (без входу) ---");
        inventory.reloadInventory("Svitoyar");
    }

    // Завдання 2 — Adapter
    static void situation2Payment() {
        GameLog.section("Ситуація 2: сторонні платіжні платформи (Adapter)");
        Player player = new Player("Svitoyar");
        BigDecimal price = new BigDecimal("99.90");

        System.out.println("--- Крок 1-2: покупка 500 од. через платформу GlobalPay ---");
        GameShop shop = new GameShop(new GlobalPayAdapter(new GlobalPayPlatform()));
        shop.buyCurrency(player, 500, price);

        System.out.println("--- Крок 3: друга платформа PayNova, код магазину не змінюється ---");
        GameShop shop2 = new GameShop(new PayNovaAdapter(new PayNovaClient()));
        shop2.buyCurrency(player, 500, price);
    }
}
