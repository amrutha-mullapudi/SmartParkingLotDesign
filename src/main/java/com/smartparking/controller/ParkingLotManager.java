package com.smartparking.controller;

import com.smartparking.core.entity.IVehicle;
import com.smartparking.helper.IReceipt;
import com.smartparking.manager.spot.IParkingSpotManager;
import com.smartparking.manager.transaction.ITransactionManager;
import com.smartparking.manager.payment.IPaymentProcessor;
import com.smartparking.manager.registry.IVehicleTypeRegistry;
import com.smartparking.workflow.IEntryManager;
import com.smartparking.workflow.IExitManager;
import com.smartparking.strategy.allocator.ISpotAllocator;
import com.smartparking.strategy.feecalculator.IFeeCalculator;
import com.smartparking.exception.ParkingException;

/**
 * Implementation of IParkingLotManager.
 * Main facade coordinating all parking lot operations.
 * Delegates operations to appropriate managers while maintaining clean API.
 */
public class ParkingLotManager implements IParkingLotManager {

    private final IParkingSpotManager spotManager;
    private final ITransactionManager transactionManager;
    private final IPaymentProcessor paymentProcessor;
    private final IVehicleTypeRegistry vehicleTypeRegistry;
    private final IEntryManager entryManager;
    private final IExitManager exitManager;
    private final ISpotAllocator allocationStrategy;
    private final IFeeCalculator feeCalculator;

    /**
     * Constructor for ParkingLotManager.
     */
    public ParkingLotManager(IParkingSpotManager spotManager,
                            ITransactionManager transactionManager,
                            IPaymentProcessor paymentProcessor,
                            IVehicleTypeRegistry vehicleTypeRegistry,
                            IEntryManager entryManager,
                            IExitManager exitManager,
                            ISpotAllocator allocationStrategy,
                            IFeeCalculator feeCalculator) {
        this.spotManager = spotManager;
        this.transactionManager = transactionManager;
        this.paymentProcessor = paymentProcessor;
        this.vehicleTypeRegistry = vehicleTypeRegistry;
        this.entryManager = entryManager;
        this.exitManager = exitManager;
        this.allocationStrategy = allocationStrategy;
        this.feeCalculator = feeCalculator;
    }

    @Override
    public String handleVehicleEntry(IVehicle vehicle) throws ParkingException {
        if (vehicle == null) {
            throw new ParkingException("Vehicle cannot be null");
        }

        return entryManager.processEntry(vehicle);
    }

    @Override
    public IReceipt handleVehicleExit(String transactionId) throws ParkingException {
        if (transactionId == null || transactionId.isEmpty()) {
            throw new ParkingException("Transaction ID cannot be null or empty");
        }

        return exitManager.processExit(transactionId);
    }

    @Override
    public double getOccupancyRate() {
        return spotManager.getOccupancyRate();
    }

    @Override
    public int getAvailableSpots() {
        return spotManager.getAvailableSpotsCount();
    }

    @Override
    public int getTotalSpots() {
        return spotManager.getTotalSpots();
    }

    @Override
    public boolean isAtFullCapacity() {
        return spotManager.isAtFullCapacity();
    }

    @Override
    public double getTotalRevenue() {
        return paymentProcessor.getTotalRevenue();
    }

    @Override
    public int getActiveTransactionCount() {
        return transactionManager.getActiveTransactionCount();
    }

    @Override
    public int getCompletedTransactionCount() {
        return transactionManager.getCompletedTransactions().length;
    }

    @Override
    public String getSystemStatus() {
        StringBuilder status = new StringBuilder();
        status.append("\n========== PARKING LOT SYSTEM STATUS ==========\n");
        status.append("Total Spots: ").append(getTotalSpots()).append("\n");
        status.append("Available Spots: ").append(getAvailableSpots()).append("\n");
        status.append("Occupancy Rate: ").append(String.format("%.2f%%", getOccupancyRate())).append("\n");
        status.append("Active Transactions: ").append(getActiveTransactionCount()).append("\n");
        status.append("Completed Transactions: ").append(getCompletedTransactionCount()).append("\n");
        status.append("Total Revenue: $").append(String.format("%.2f", getTotalRevenue())).append("\n");
        status.append("Allocation Strategy: ").append(getAllocationStrategy()).append("\n");
        status.append("Fee Calculation Strategy: ").append(getFeeCalculationStrategy()).append("\n");
        status.append("==============================================\n");
        return status.toString();
    }

    @Override
    public String getAllocationStrategy() {
        return allocationStrategy.getStrategyName();
    }

    @Override
    public String getFeeCalculationStrategy() {
        return feeCalculator.getStrategyName();
    }

    /**
     * Get spot manager for advanced operations.
     */
    public IParkingSpotManager getSpotManager() {
        return spotManager;
    }

    /**
     * Get transaction manager for advanced operations.
     */
    public ITransactionManager getTransactionManager() {
        return transactionManager;
    }

    /**
     * Get payment processor for advanced operations.
     */
    public IPaymentProcessor getPaymentProcessor() {
        return paymentProcessor;
    }

    /**
     * Get vehicle type registry for advanced operations.
     */
    public IVehicleTypeRegistry getVehicleTypeRegistry() {
        return vehicleTypeRegistry;
    }

    @Override
    public String toString() {
        return getSystemStatus();
    }
}
