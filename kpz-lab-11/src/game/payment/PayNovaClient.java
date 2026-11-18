package game.payment;

import game.common.GameLog;

/** Adaptee №2 (інший постачальник): числовий код валюти ISO 4217, відповідь числовим кодом. */
public class PayNovaClient {
    private int counter = 0;

    public int executePayment(int amountMinor, int currencyIso, String account) {
        counter++;
        GameLog.log("PayNovaClient", "executePayment(amountMinor=" + amountMinor + ", iso=" + currencyIso
                + ", account=" + account + ")");
        return 0; // 0 = успіх, 51 = недостатньо коштів, інше = відмова банку
    }

    public String lastReference() {
        return "PN-" + (7000 + counter);
    }
}
