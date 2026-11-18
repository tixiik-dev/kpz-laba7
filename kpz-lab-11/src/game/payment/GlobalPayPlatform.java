package game.payment;

import game.common.GameLog;

/** Adaptee №1 (сторонній код, змінювати не можна): сума в центах, валюта рядковим кодом, відповідь рядком. */
public class GlobalPayPlatform {
    private int counter = 0;

    public String createCharge(long amountCents, String currencyCode, String customerRef) {
        counter++;
        GameLog.log("GlobalPayPlatform", "createCharge(amountCents=" + amountCents + ", currency=" + currencyCode
                + ", customer=" + customerRef + ")");
        return "status=succeeded;id=ch_" + (1000 + counter);
    }
}
