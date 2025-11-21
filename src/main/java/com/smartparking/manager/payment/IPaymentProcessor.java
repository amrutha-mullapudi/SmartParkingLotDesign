package com.smartparking.manager.payment;

import com.smartparking.core.entity.IPaymentRecord;
import com.smartparking.constant.PaymentMethod;
import com.smartparking.constant.PaymentStatus;
import com.smartparking.exception.PaymentFailedException;

/**
 * Interface for managing payment processing in the parking lot system.
 * Handles payment records, status tracking, and payment validation.
 */
public interface IPaymentProcessor {

    /**
     * Create a new payment record for a completed parking transaction.
     * @param transactionId the transaction being paid
     * @param amount the payment amount
     * @param paymentMethod the PaymentMethod used
     * @return the created IPaymentRecord
     * @throws PaymentFailedException if payment record creation fails
     */
    IPaymentRecord createPaymentRecord(String transactionId, double amount, PaymentMethod paymentMethod) 
            throws PaymentFailedException;

    /**
     * Get a payment record by its ID.
     * @param paymentId the unique identifier of the payment
     * @return the IPaymentRecord if found, null otherwise
     */
    IPaymentRecord getPaymentRecord(String paymentId);

    /**
     * Process a payment and mark it as completed.
     * @param paymentId the payment ID to process
     * @param gatewayChargeId the reference ID from payment gateway
     * @return true if processing successful
     * @throws PaymentFailedException if payment processing fails
     */
    boolean processPayment(String paymentId, String gatewayChargeId) 
            throws PaymentFailedException;

    /**
     * Mark a payment as failed.
     * @param paymentId the payment ID that failed
     * @param reason optional reason for failure
     * @return true if update successful
     * @throws PaymentFailedException if operation fails
     */
    boolean failPayment(String paymentId, String reason) 
            throws PaymentFailedException;

    /**
     * Refund a completed payment.
     * @param paymentId the payment ID to refund
     * @return true if refund successful
     * @throws PaymentFailedException if refund fails
     */
    boolean refundPayment(String paymentId) 
            throws PaymentFailedException;

    /**
     * Get all payment records for a specific transaction.
     * @param transactionId the transaction ID to search for
     * @return array of IPaymentRecord objects for this transaction
     */
    IPaymentRecord[] getTransactionPayments(String transactionId);

    /**
     * Get all payments by status.
     * @param status the PaymentStatus to filter by
     * @return array of IPaymentRecord objects with matching status
     */
    IPaymentRecord[] getPaymentsByStatus(PaymentStatus status);

    /**
     * Get all payments by method.
     * @param method the PaymentMethod to filter by
     * @return array of IPaymentRecord objects using this method
     */
    IPaymentRecord[] getPaymentsByMethod(PaymentMethod method);

    /**
     * Calculate total revenue from completed payments.
     * @return total revenue amount
     */
    double getTotalRevenue();

    /**
     * Get count of all payment records.
     * @return total payment count
     */
    int getPaymentCount();

    /**
     * Get count of completed payments.
     * @return count of COMPLETED status payments
     */
    int getCompletedPaymentCount();

    /**
     * Get count of failed payments.
     * @return count of FAILED status payments
     */
    int getFailedPaymentCount();
}
