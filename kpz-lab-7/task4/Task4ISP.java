package task4;

import java.math.BigDecimal;

public class Task4ISP {
    public static void main(String[] args) {
        BigDecimal amount = new BigDecimal("150.00");


        OnlineWebCheckoutTerminal webCheckout = new OnlineWebCheckoutTerminal();
        webCheckout.processCreditCard(amount, "4111-2222-3333-4444");
        webCheckout.processBankTransfer(amount, "UA123456789012345678901234567");

        System.out.println("----------------------------------------");

        PosCardReader posTerminal = new PosCardReader();
        posTerminal.processCreditCard(amount, "5168-0000-1111-2222");
        posTerminal.processNfcTap(amount);
        posTerminal.printPaperReceipt("Чек №9821. Сума: 150.00 UAH. Успішно.");

        System.out.println("----------------------------------------");

        NfcPaymentProcessor nfcService = new NfcPaymentProcessor();
        nfcService.payViaNfc(posTerminal, new BigDecimal("45.50"));
    }
}


interface CardPaymentProcessable {
    void processCreditCard(BigDecimal amount, String cardNumber);
}

interface NfcPaymentProcessable {
    void processNfcTap(BigDecimal amount);
}

interface BankTransferProcessable {
    void processBankTransfer(BigDecimal amount, String iban);
}

interface GiftCardProcessable {
    void processGiftCard(BigDecimal amount, String voucherCode);
}

interface ReceiptPrintable {
    void printPaperReceipt(String text);
}


class OnlineWebCheckoutTerminal implements CardPaymentProcessable, BankTransferProcessable {
    @Override
    public void processCreditCard(BigDecimal amount, String cardNumber) {
        validateAmount(amount);
        System.out.println("[Web Checkout] Charged " + amount + " UAH via card " + maskCard(cardNumber));
    }

    @Override
    public void processBankTransfer(BigDecimal amount, String iban) {
        validateAmount(amount);
        System.out.println("[Web Checkout] Generated IBAN invoice for " + amount + " UAH to " + iban);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }

    private String maskCard(String cardNumber) {
        if (cardNumber == null || cardNumber.length() < 4) return "****";
        return "****-****-****-" + cardNumber.substring(cardNumber.length() - 4);
    }
}


class PosCardReader implements CardPaymentProcessable, NfcPaymentProcessable, ReceiptPrintable {
    @Override
    public void processCreditCard(BigDecimal amount, String cardNumber) {
        validateAmount(amount);
        System.out.println("[POS Terminal] Processed physical card payment: " + amount + " UAH");
    }

    @Override
    public void processNfcTap(BigDecimal amount) {
        validateAmount(amount);
        System.out.println("[POS Terminal] Contactless NFC tap payment: " + amount + " UAH");
    }

    @Override
    public void printPaperReceipt(String text) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("Receipt text cannot be empty");
        }
        System.out.println("[POS Printer] PRINTING RECEIPT:\n" + text);
    }

    private void validateAmount(BigDecimal amount) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Amount must be positive");
        }
    }
}

class NfcPaymentProcessor {
    public void payViaNfc(NfcPaymentProcessable nfcDevice, BigDecimal amount) {
        if (nfcDevice == null) {
            throw new IllegalArgumentException("NFC device is required");
        }
        nfcDevice.processNfcTap(amount);
    }
}