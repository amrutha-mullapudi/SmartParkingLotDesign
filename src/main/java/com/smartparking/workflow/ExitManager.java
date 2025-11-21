package com.smartparking.workflow;

import com.smartparking.core.entity.ITransaction;
import com.smartparking.core.entity.IPaymentRecord;
import com.smartparking.core.entity.IVehicle;
import com.smartparking.helper.IReceipt;
import com.smartparking.helper.Receipt;
import com.smartparking.helper.PricingContext;
import com.smartparking.manager.spot.IParkingSpotManager;
import com.smartparking.manager.transaction.ITransactionManager;
import com.smartparking.manager.payment.IPaymentProcessor;
import com.smartparking.strategy.feecalculator.IFeeCalculator;
import com.smartparking.constant.PaymentMethod;
import com.smartparking.exception.ParkingException;
import com.smartparking.exception.TransactionException;
import java.util.UUID;

/**
 * Implementation of IExitManager.
 * Orchestrates the vehicle exit workflow.
 */
public class ExitManager implements IExitManager {

    private final ITransactionManager transactionManager;
    private final IParkingSpotManager spotManager;
    private final IPaymentProcessor paymentProcessor;
    private final IFeeCalculator feeCalculator;
    private int totalExitsProcessed;

    /**
     * Constructor for ExitManager.
     * @param transactionManager the ITransactionManager to use
     * @param spotManager the IParkingSpotManager to use
     * @param paymentProcessor the IPaymentProcessor to use
     * @param feeCalculator the IFeeCalculator strategy to use
     */
    public ExitManager(ITransactionManager transactionManager, IParkingSpotManager spotManager,
                      IPaymentProcessor paymentProcessor, IFeeCalculator feeCalculator) {
        this.transactionManager = transactionManager;
        this.spotManager = spotManager;
        this.paymentProcessor = paymentProcessor;
        this.feeCalculator = feeCalculator;
        this.totalExitsProcessed = 0;
    }

    @Override
    public IReceipt processExit(String transactionId) throws ParkingException {
        if (transactionId == null || transactionId.isEmpty()) {
            throw new TransactionException("Invalid transaction ID");
        }

        // Get transaction
        ITransaction transaction = transactionManager.getTransaction(transactionId);
        if (transaction == null) {
            throw new TransactionException(transactionId, "Transaction not found");
        }

        // Record exit
        long exitTime = System.currentTimeMillis();
        if (!transactionManager.recordExit(transactionId, exitTime)) {
            throw new TransactionException(transactionId, "Failed to record exit");
        }

        // Get updated transaction with exit time
        transaction = transactionManager.getTransaction(transactionId);

        // Calculate fee
        double fee = feeCalculator.calculateFee(transaction.getDurationInMinutes(), null);
        transaction.setCalculatedFee(fee);

        // Release parking spots
        String[] spotIds = transaction.getSpotIds();
        for (String spotId : spotIds) {
            String releasedVehicleId = spotManager.releaseSpot(spotId);
            if (releasedVehicleId == null) {
                throw new TransactionException(transactionId, "Failed to release spot: " + spotId);
            }
        }

        // Create payment record
        IPaymentRecord payment = paymentProcessor.createPaymentRecord(
            transactionId,
            fee,
            PaymentMethod.CREDIT_CARD // Default payment method
        );

        if (payment == null) {
            throw new TransactionException(transactionId, "Failed to create payment record");
        }

        // Process payment
        String gatewayChargeId = generateChargeId();
        if (!paymentProcessor.processPayment(payment.getPaymentId(), gatewayChargeId)) {
            throw new TransactionException(transactionId, "Payment processing failed");
        }

        // Mark transaction as complete
        transaction.setPaymentId(payment.getPaymentId());
        transactionManager.completeTransaction(transactionId);

        totalExitsProcessed++;

        // Generate receipt
        String receiptId = "RCP-" + UUID.randomUUID().toString();
        return new Receipt(
            receiptId,
            transactionId,
            "UNKNOWN", // Vehicle license plate not readily available here
            transaction.getDurationInMinutes(),
            fee,
            PaymentMethod.CREDIT_CARD,
            transaction.getEntryTime(),
            exitTime,
            payment.getStatus().getDisplayName()
        );
    }

    @Override
    public String getExitStats() {
        return "ExitManager{" +
                "totalExitsProcessed=" + totalExitsProcessed +
                ", totalRevenue=$" + String.format("%.2f", paymentProcessor.getTotalRevenue()) +
                ", completedPayments=" + paymentProcessor.getCompletedPaymentCount() +
                '}';
    }

    /**
     * Generate a unique charge ID for payment gateway.
     */
    private String generateChargeId() {
        return "CHG-" + UUID.randomUUID().toString();
    }

    /**
     * Get total exits processed.
     */
    public int getTotalExitsProcessed() {
        return totalExitsProcessed;
    }
}
