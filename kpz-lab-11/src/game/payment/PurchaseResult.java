package game.payment;

/** Результат покупки у форматі внутрішнього магазину гри. */
public record PurchaseResult(boolean success, String transactionId, String failureReason) {
    public static PurchaseResult ok(String transactionId) {
        return new PurchaseResult(true, transactionId, null);
    }

    public static PurchaseResult fail(String reason) {
        return new PurchaseResult(false, null, reason);
    }
}
