package com.smartparking.core.entity;

import com.smartparking.constant.PaymentStatus;
import com.smartparking.constant.PaymentMethod;

/**
 * Interface defining the contract for a payment record in the parking lot system.
 * A payment record tracks payment details and status for a completed parking transaction.
 */
public interface IPaymentRecord {

    /**
     * Get the unique identifier for this payment record.
     * @return unique payment ID (typically UUID)
     */
    String getPaymentId();

    /**
     * Get the associated transaction ID.
     * @return transaction ID
     */
    String getTransactionId();

    /**
     * Get the payment amount.
     * @return amount in currency (e.g., dollars, rupees)
     */
    double getAmount();

    /**
     * Get the payment method used.
     * @return the PaymentMethod enum value
     */
    PaymentMethod getPaymentMethod();

    /**
     * Get the current status of this payment.
     * @return the PaymentStatus enum value
     */
    PaymentStatus getStatus();

    /**
     * Get the gateway charge ID or reference from payment gateway.
     * For cash payments, this might be a receipt number.
     * For card payments, this is the transaction reference from the payment gateway.
     * @return gateway reference ID
     */
    String getGatewayChargeId();

    /**
     * Set the gateway charge ID after payment is processed.
     * @param gatewayChargeId the reference from payment gateway
     */
    void setGatewayChargeId(String gatewayChargeId);

    /**
     * Get the timestamp when payment was processed.
     * @return processing timestamp in milliseconds
     */
    long getProcessedAt();

    /**
     * Set the status of this payment record.
     * @param status the new PaymentStatus
     */
    void setStatus(PaymentStatus status);

    /**
     * Get payment details as a formatted string.
     * @return payment information summary
     */
    String getPaymentDetails();
}
