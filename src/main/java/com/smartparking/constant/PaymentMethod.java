package com.smartparking.constant;

/**
 * Enumeration representing different payment methods accepted by the parking lot system.
 */
public enum PaymentMethod {
    CASH("Cash"),
    CREDIT_CARD("CreditCard"),
    DEBIT_CARD("DebitCard"),
    DIGITAL_WALLET("DigitalWallet"),
    UPI("UPI"),
    PREPAID_ACCOUNT("PrepaidAccount");

    private final String displayName;

    PaymentMethod(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
