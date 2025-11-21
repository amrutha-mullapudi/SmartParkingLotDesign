package com.smartparking.core.impl;

import com.smartparking.constant.PaymentStatus;
import com.smartparking.constant.PaymentMethod;
import com.smartparking.core.entity.IPaymentRecord;

/**
 * Implementation of IPaymentRecord representing a payment for a parking transaction.
 * Tracks payment details, method, status, and gateway information.
 */
public class PaymentRecord implements IPaymentRecord {

    private final String paymentId;
    private final String transactionId;
    private final double amount;
    private final PaymentMethod paymentMethod;
    private final long processedAt;
    private PaymentStatus status;
    private String gatewayChargeId;

    /**
     * Constructor for PaymentRecord.
     * @param paymentId unique identifier
     * @param transactionId associated transaction ID
     * @param amount payment amount
     * @param paymentMethod method of payment
     * @param processedAt timestamp when payment was processed
     */
    public PaymentRecord(String paymentId, String transactionId, double amount,
                        PaymentMethod paymentMethod, long processedAt) {
        this.paymentId = paymentId;
        this.transactionId = transactionId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.processedAt = processedAt;
        this.status = PaymentStatus.PENDING;
        this.gatewayChargeId = null;
    }

    @Override
    public String getPaymentId() {
        return paymentId;
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }

    @Override
    public double getAmount() {
        return amount;
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public PaymentStatus getStatus() {
        return status;
    }

    @Override
    public String getGatewayChargeId() {
        return gatewayChargeId;
    }

    @Override
    public void setGatewayChargeId(String gatewayChargeId) {
        this.gatewayChargeId = gatewayChargeId;
    }

    @Override
    public long getProcessedAt() {
        return processedAt;
    }

    @Override
    public void setStatus(PaymentStatus status) {
        this.status = status;
    }

    @Override
    public String getPaymentDetails() {
        return String.format(
            "PaymentRecord{id='%s', txId='%s', amount=$%.2f, method=%s, status=%s}",
            paymentId, transactionId, amount, paymentMethod, status
        );
    }

    @Override
    public String toString() {
        return getPaymentDetails();
    }
}
