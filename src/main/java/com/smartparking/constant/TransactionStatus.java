package com.smartparking.constant;

/**
 * Enumeration representing the status of a parking transaction.
 */
public enum TransactionStatus {
    IN_PROGRESS("InProgress"),   // Vehicle is currently parked
    COMPLETED("Completed"),      // Vehicle has exited, fee calculated
    PAID("Paid"),                // Payment has been processed
    CANCELLED("Cancelled");      // Transaction was cancelled

    private final String displayName;

    TransactionStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
