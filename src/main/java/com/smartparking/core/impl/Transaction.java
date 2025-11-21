package com.smartparking.core.impl;

import com.smartparking.constant.TransactionStatus;
import com.smartparking.core.entity.ITransaction;

/**
 * Implementation of ITransaction representing a parking session.
 * Tracks vehicle entry, exit, duration, and fee calculation.
 */
public class Transaction implements ITransaction {

    private final String transactionId;
    private final String vehicleId;
    private final String[] spotIds;
    private final long entryTime;
    private long exitTime;
    private double calculatedFee;
    private String paymentId;
    private TransactionStatus status;

    /**
     * Constructor for Transaction.
     * @param transactionId unique identifier
     * @param vehicleId ID of the parked vehicle
     * @param spotIds IDs of the parking spots occupied
     * @param entryTime entry timestamp in milliseconds
     */
    public Transaction(String transactionId, String vehicleId, String[] spotIds, long entryTime) {
        this.transactionId = transactionId;
        this.vehicleId = vehicleId;
        this.spotIds = spotIds;
        this.entryTime = entryTime;
        this.exitTime = 0;
        this.calculatedFee = 0.0;
        this.paymentId = null;
        this.status = TransactionStatus.IN_PROGRESS;
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }

    @Override
    public String getVehicleId() {
        return vehicleId;
    }

    @Override
    public String[] getSpotIds() {
        return spotIds;
    }

    @Override
    public long getEntryTime() {
        return entryTime;
    }

    @Override
    public long getExitTime() {
        return exitTime;
    }

    @Override
    public int getDurationInMinutes() {
        if (exitTime == 0) {
            return 0; // Vehicle still parked
        }
        return (int) ((exitTime - entryTime) / 60000);
    }

    @Override
    public double getCalculatedFee() {
        return calculatedFee;
    }

    @Override
    public void setCalculatedFee(double fee) {
        this.calculatedFee = fee;
    }

    @Override
    public TransactionStatus getStatus() {
        return status;
    }

    @Override
    public boolean recordExit(long exitTime) {
        if (exitTime <= entryTime) {
            return false;
        }

        this.exitTime = exitTime;
        this.status = TransactionStatus.COMPLETED;
        return true;
    }

    @Override
    public String getPaymentId() {
        return paymentId;
    }

    @Override
    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    @Override
    public void complete() {
        this.status = TransactionStatus.PAID;
    }

    @Override
    public String getTransactionDetails() {
        return String.format(
            "Transaction{id='%s', vehicleId='%s', duration=%d min, fee=$%.2f, status=%s}",
            transactionId, vehicleId, getDurationInMinutes(), calculatedFee, status
        );
    }

    @Override
    public String toString() {
        return getTransactionDetails();
    }
}
