package game.payment;

import game.common.GameLog;
import java.math.BigDecimal;

/** Adapter №1: перетворення гривень у центи, назв методів та розбір рядкової відповіді в одному місці. */
public class GlobalPayAdapter implements ShopPaymentGateway {
    private final GlobalPayPlatform platform;

    public GlobalPayAdapter(GlobalPayPlatform platform) {
        this.platform = platform;
    }

    @Override
    public PurchaseResult purchase(String playerName, BigDecimal amountUah, int gameCurrencyAmount) {
        long cents = amountUah.setScale(2).movePointRight(2).longValueExact();
        GameLog.log("GlobalPayAdapter", "Перетворення: " + amountUah + " грн -> " + cents + " центів, валюта UAH");
        String response = platform.createCharge(cents, "UAH", playerName);
        GameLog.log("GlobalPayAdapter", "Відповідь платформи (сирий формат): " + response);
        return parse(response);
    }

    private PurchaseResult parse(String response) {
        String status = null, id = null, reason = null;
        for (String part : response.split(";")) {
            String[] kv = part.split("=", 2);
            switch (kv[0]) {
                case "status" -> status = kv[1];
                case "id" -> id = kv[1];
                case "reason" -> reason = kv[1];
                default -> { }
            }
        }
        return "succeeded".equals(status) ? PurchaseResult.ok(id)
                : PurchaseResult.fail(reason == null ? "Платіж відхилено" : reason);
    }
}
