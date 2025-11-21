package com.smartparking.core.entity;

import com.smartparking.constant.TransactionStatus;

/**
 * Interface defining the contract for a parking transaction in the parking lot system.
 * A transaction represents a complete parking session: from vehicle entry to exit,
 * fee calculation, and payment processing.
 */
public interface ITransaction {

    /**
     * Get the unique identifier for this transaction.
     * @return unique transaction ID (typically UUID)
     */
    String getTransactionId();

    /**
     * Get the vehicle ID associated with this transaction.
     * @return vehicle ID
     */
    String getVehicleId();

    /**
     * Get the parking spot IDs where the vehicle was parked during this transaction.
     * For multi-spot vehicles, this will be an array.
     * @return array of spot IDs
     */
    String[] getSpotIds();

    /**
     * Get the entry time (when vehicle entered the parking lot).
     * @return entry timestamp in milliseconds (Unix epoch)
     */
    long getEntryTime();

    /**
     * Get the exit time (when vehicle left the parking lot).
     * @return exit timestamp in milliseconds, 0 if vehicle hasn't exited yet
     */
    long getExitTime();

    /**
     * Get the total parking duration in minutes.
     * Calculated as (exitTime - entryTime) / 60000.
     * @return duration in minutes, 0 if vehicle hasn't exited yet
     */
    int getDurationInMinutes();

    /**
     * Get the calculated parking fee for this transaction.
     * @return fee amount, 0 if fee hasn't been calculated yet
     */
    double getCalculatedFee();

    /**
     * Set the calculated parking fee.
     * @param fee the fee amount
     */
    void setCalculatedFee(double fee);

    /**
     * Get the current status of this transaction.
     * @return the TransactionStatus enum value
     */
    TransactionStatus getStatus();

    /**
     * Record the vehicle exit and calculate parking duration.
     * @param exitTime the exit timestamp in milliseconds
     * @return true if exit recording was successful, false otherwise
     */
    boolean recordExit(long exitTime);

    /**
     * Get the associated payment record ID for this transaction.
     * @return payment record ID, null if payment hasn't been recorded yet
     */
    String getPaymentId();

    /**
     * Set the associated payment record ID.
     * @param paymentId the payment record ID
     */
    void setPaymentId(String paymentId);

    /**
     * Mark this transaction as completed.
     * Typically called after fee is calculated and payment is processed.
     */
    void complete();

    /**
     * Get a summary of the transaction details.
     * @return formatted string with transaction information
     */
    String getTransactionDetails();
}
