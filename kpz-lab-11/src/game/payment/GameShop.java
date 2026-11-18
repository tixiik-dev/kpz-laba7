package game.payment;

import game.common.GameLog;
import java.math.BigDecimal;

/** Client: магазин гри. Знає лише про ShopPaymentGateway. */
public class GameShop {
    private final ShopPaymentGateway gateway;

    public GameShop(ShopPaymentGateway gateway) {
        this.gateway = gateway;
    }

    public boolean buyCurrency(Player player, int amount, BigDecimal priceUah) {
        GameLog.log("GameShop", player.getName() + " купує " + amount + " од. валюти за " + priceUah + " грн");
        PurchaseResult result = gateway.purchase(player.getName(), priceUah, amount);
        if (result.success()) {
            player.credit(amount);
            GameLog.log("GameShop", "Покупка успішна (" + result.transactionId() + "), баланс "
                    + player.getName() + ": " + player.getGameCurrency());
            return true;
        }
        GameLog.log("GameShop", "Покупка відхилена: " + result.failureReason());
        return false;
    }
}
