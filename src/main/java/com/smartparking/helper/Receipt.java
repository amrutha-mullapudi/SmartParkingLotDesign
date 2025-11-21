package com.smartparking.helper;

import com.smartparking.constant.PaymentMethod;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Implementation of IReceipt for parking transaction receipts.
 * Generates formatted receipts for customer records.
 */
public class Receipt implements IReceipt {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private final String receiptId;
    private final String transactionId;
    private final String vehicleLicensePlate;
    private final int durationInMinutes;
    private final double parkingFee;
    private final PaymentMethod paymentMethod;
    private final long entryTime;
    private final long exitTime;
    private final String paymentStatus;

    /**
     * Constructor for Receipt.
     */
    public Receipt(String receiptId, String transactionId, String vehicleLicensePlate,
                   int durationInMinutes, double parkingFee, PaymentMethod paymentMethod,
                   long entryTime, long exitTime, String paymentStatus) {
        this.receiptId = receiptId;
        this.transactionId = transactionId;
        this.vehicleLicensePlate = vehicleLicensePlate;
        this.durationInMinutes = durationInMinutes;
        this.parkingFee = parkingFee;
        this.paymentMethod = paymentMethod;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
        this.paymentStatus = paymentStatus;
    }

    @Override
    public String getReceiptId() {
        return receiptId;
    }

    @Override
    public String getTransactionId() {
        return transactionId;
    }

    @Override
    public String getVehicleLicensePlate() {
        return vehicleLicensePlate;
    }

    @Override
    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    @Override
    public double getParkingFee() {
        return parkingFee;
    }

    @Override
    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    @Override
    public String getEntryTimeFormatted() {
        return DATE_FORMAT.format(new Date(entryTime));
    }

    @Override
    public String getExitTimeFormatted() {
        return DATE_FORMAT.format(new Date(exitTime));
    }

    @Override
    public String getPaymentStatus() {
        return paymentStatus;
    }

    @Override
    public String getFormattedReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("========== PARKING RECEIPT ==========\n");
        receipt.append("Receipt ID: ").append(receiptId).append("\n");
        receipt.append("Transaction ID: ").append(transactionId).append("\n");
        receipt.append("License Plate: ").append(vehicleLicensePlate).append("\n");
        receipt.append("Entry Time: ").append(getEntryTimeFormatted()).append("\n");
        receipt.append("Exit Time: ").append(getExitTimeFormatted()).append("\n");
        receipt.append("Duration: ").append(durationInMinutes).append(" minutes\n");
        receipt.append("Parking Fee: $").append(String.format("%.2f", parkingFee)).append("\n");
        receipt.append("Payment Method: ").append(paymentMethod.getDisplayName()).append("\n");
        receipt.append("Payment Status: ").append(paymentStatus).append("\n");
        receipt.append("====================================\n");
        return receipt.toString();
    }

    @Override
    public String toString() {
        return getFormattedReceipt();
    }
}
