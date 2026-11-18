package game.payment;

import java.math.BigDecimal;

/** Target: власний інтерфейс покупки внутрішнього магазину (сума в гривнях). */
public interface ShopPaymentGateway {
    PurchaseResult purchase(String playerName, BigDecimal amountUah, int gameCurrencyAmount);
}
