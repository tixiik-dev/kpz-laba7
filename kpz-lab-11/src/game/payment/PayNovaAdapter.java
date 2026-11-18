package game.payment;

import game.common.GameLog;
import java.math.BigDecimal;

/** Adapter №2: інший формат (ISO-код валюти, числовий код відповіді). Магазин про це не знає. */
public class PayNovaAdapter implements ShopPaymentGateway {
    private static final int ISO_UAH = 980;
    private final PayNovaClient client;

    public PayNovaAdapter(PayNovaClient client) {
        this.client = client;
    }

    @Override
    public PurchaseResult purchase(String playerName, BigDecimal amountUah, int gameCurrencyAmount) {
        int minor = amountUah.setScale(2).movePointRight(2).intValueExact();
        GameLog.log("PayNovaAdapter", "Перетворення: " + amountUah + " грн -> " + minor + " коп., ISO " + ISO_UAH);
        int code = client.executePayment(minor, ISO_UAH, playerName);
        GameLog.log("PayNovaAdapter", "Код відповіді платформи: " + code);
        return switch (code) {
            case 0 -> PurchaseResult.ok(client.lastReference());
            case 51 -> PurchaseResult.fail("Недостатньо коштів");
            default -> PurchaseResult.fail("Платіж відхилено банком (код " + code + ")");
        };
    }
}
