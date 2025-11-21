package com.smartparking.manager.payment;

import com.smartparking.core.entity.IPaymentRecord;
import com.smartparking.core.impl.PaymentRecord;
import com.smartparking.constant.PaymentMethod;
import com.smartparking.constant.PaymentStatus;
import com.smartparking.exception.PaymentFailedException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of IPaymentProcessor.
 * Manages payment records, processing, and revenue tracking.
 */
public class PaymentProcessor implements IPaymentProcessor {

    private final Map<String, IPaymentRecord> allPayments;

    /**
     * Constructor for PaymentProcessor.
     */
    public PaymentProcessor() {
        this.allPayments = new HashMap<>();
    }

    @Override
    public IPaymentRecord createPaymentRecord(String transactionId, double amount, PaymentMethod paymentMethod) 
            throws PaymentFailedException {
        if (transactionId == null || transactionId.isEmpty()) {
            throw new PaymentFailedException("Transaction ID cannot be null or empty");
        }
        
        if (amount <= 0) {
            throw new PaymentFailedException(transactionId, "Invalid payment amount: " + amount + ". Amount must be greater than 0");
        }
        
        if (paymentMethod == null) {
            throw new PaymentFailedException(transactionId, "Payment method cannot be null");
        }

        String paymentId = generatePaymentId();
        long processedAt = System.currentTimeMillis();

        IPaymentRecord paymentRecord = new PaymentRecord(paymentId, transactionId, amount, paymentMethod, processedAt);
        allPayments.put(paymentId, paymentRecord);

        return paymentRecord;
    }

    @Override
    public IPaymentRecord getPaymentRecord(String paymentId) {
        if (paymentId == null) {
            return null;
        }
        return allPayments.get(paymentId);
    }

    @Override
    public boolean processPayment(String paymentId, String gatewayChargeId) 
            throws PaymentFailedException {
        if (paymentId == null || paymentId.isEmpty()) {
            throw new PaymentFailedException("Payment ID cannot be null or empty");
        }
        
        if (gatewayChargeId == null || gatewayChargeId.isEmpty()) {
            throw new PaymentFailedException(paymentId, "Gateway charge ID cannot be null or empty");
        }

        IPaymentRecord paymentRecord = allPayments.get(paymentId);
        if (paymentRecord == null) {
            throw new PaymentFailedException(paymentId, "Payment record not found");
        }

        paymentRecord.setGatewayChargeId(gatewayChargeId);
        paymentRecord.setStatus(PaymentStatus.COMPLETED);
        return true;
    }

    @Override
    public boolean failPayment(String paymentId, String reason) 
            throws PaymentFailedException {
        if (paymentId == null || paymentId.isEmpty()) {
            throw new PaymentFailedException("Payment ID cannot be null or empty");
        }

        IPaymentRecord paymentRecord = allPayments.get(paymentId);
        if (paymentRecord == null) {
            throw new PaymentFailedException(paymentId, "Payment record not found");
        }

        paymentRecord.setStatus(PaymentStatus.FAILED);
        return true;
    }

    @Override
    public boolean refundPayment(String paymentId) 
            throws PaymentFailedException {
        if (paymentId == null || paymentId.isEmpty()) {
            throw new PaymentFailedException("Payment ID cannot be null or empty");
        }

        IPaymentRecord paymentRecord = allPayments.get(paymentId);
        if (paymentRecord == null) {
            throw new PaymentFailedException(paymentId, "Payment record not found");
        }

        // Only refund if payment was completed
        if (paymentRecord.getStatus() != PaymentStatus.COMPLETED) {
            throw new PaymentFailedException(paymentId, 
                "Cannot refund payment with status: " + paymentRecord.getStatus().getDisplayName());
        }

        paymentRecord.setStatus(PaymentStatus.REFUNDED);
        return true;
    }

    @Override
    public IPaymentRecord[] getTransactionPayments(String transactionId) {
        if (transactionId == null) {
            return new IPaymentRecord[0];
        }

        List<IPaymentRecord> transactionPaymentsList = new ArrayList<>();
        for (IPaymentRecord payment : allPayments.values()) {
            if (payment.getTransactionId().equals(transactionId)) {
                transactionPaymentsList.add(payment);
            }
        }
        return transactionPaymentsList.toArray(new IPaymentRecord[0]);
    }

    @Override
    public IPaymentRecord[] getPaymentsByStatus(PaymentStatus status) {
        if (status == null) {
            return new IPaymentRecord[0];
        }

        List<IPaymentRecord> statusPaymentsList = new ArrayList<>();
        for (IPaymentRecord payment : allPayments.values()) {
            if (payment.getStatus() == status) {
                statusPaymentsList.add(payment);
            }
        }
        return statusPaymentsList.toArray(new IPaymentRecord[0]);
    }

    @Override
    public IPaymentRecord[] getPaymentsByMethod(PaymentMethod method) {
        if (method == null) {
            return new IPaymentRecord[0];
        }

        List<IPaymentRecord> methodPaymentsList = new ArrayList<>();
        for (IPaymentRecord payment : allPayments.values()) {
            if (payment.getPaymentMethod() == method) {
                methodPaymentsList.add(payment);
            }
        }
        return methodPaymentsList.toArray(new IPaymentRecord[0]);
    }

    @Override
    public double getTotalRevenue() {
        double totalRevenue = 0.0;
        for (IPaymentRecord payment : allPayments.values()) {
            // Only count completed payments (not failed or refunded)
            if (payment.getStatus() == PaymentStatus.COMPLETED) {
                totalRevenue += payment.getAmount();
            }
        }
        return totalRevenue;
    }

    @Override
    public int getPaymentCount() {
        return allPayments.size();
    }

    @Override
    public int getCompletedPaymentCount() {
        return getPaymentsByStatus(PaymentStatus.COMPLETED).length;
    }

    @Override
    public int getFailedPaymentCount() {
        return getPaymentsByStatus(PaymentStatus.FAILED).length;
    }

    /**
     * Generate a unique payment ID.
     */
    private String generatePaymentId() {
        return "PAY-" + UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "PaymentProcessor{" +
                "totalPayments=" + allPayments.size() +
                ", completedPayments=" + getCompletedPaymentCount() +
                ", totalRevenue=$" + String.format("%.2f", getTotalRevenue()) +
                '}';
    }
}
