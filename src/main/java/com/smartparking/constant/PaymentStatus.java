package com.smartparking.constant;

/**
 * Enumeration representing the status of a payment record.
 */
public enum PaymentStatus {
    PENDING("Pending"),              // Payment awaiting processing
    PROCESSING("Processing"),        // Payment is being processed
    COMPLETED("Completed"),          // Payment successfully completed
    FAILED("Failed"),                // Payment failed
    REFUNDED("Refunded"),            // Payment was refunded
    DISPUTED("Disputed");            // Payment is under dispute

    private final String displayName;

    PaymentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
