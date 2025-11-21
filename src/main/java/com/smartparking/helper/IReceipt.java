package com.smartparking.helper;

import com.smartparking.constant.PaymentMethod;

/**
 * Interface for parking receipt generation and management.
 * Provides receipt information for completed transactions.
 */
public interface IReceipt {

    /**
     * Get the receipt ID.
     * @return unique receipt identifier
     */
    String getReceiptId();

    /**
     * Get the transaction ID associated with this receipt.
     * @return transaction ID
     */
    String getTransactionId();

    /**
     * Get the vehicle license plate.
     * @return license plate
     */
    String getVehicleLicensePlate();

    /**
     * Get the parking duration in minutes.
     * @return duration
     */
    int getDurationInMinutes();

    /**
     * Get the calculated parking fee.
     * @return fee amount
     */
    double getParkingFee();

    /**
     * Get the payment method used.
     * @return PaymentMethod
     */
    PaymentMethod getPaymentMethod();

    /**
     * Get the entry time (formatted string).
     * @return entry time display
     */
    String getEntryTimeFormatted();

    /**
     * Get the exit time (formatted string).
     * @return exit time display
     */
    String getExitTimeFormatted();

    /**
     * Get the payment status.
     * @return payment status string
     */
    String getPaymentStatus();

    /**
     * Get the full receipt as a formatted string.
     * @return formatted receipt
     */
    String getFormattedReceipt();
}
