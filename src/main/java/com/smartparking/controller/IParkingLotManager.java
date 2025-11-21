package com.smartparking.controller;

import com.smartparking.core.entity.IVehicle;
import com.smartparking.helper.IReceipt;
import com.smartparking.exception.ParkingException;

/**
 * Interface for the main parking lot management system.
 * Acts as a facade providing unified access to all parking lot operations.
 * Clients interact only with this interface for all parking lot functionality.
 */
public interface IParkingLotManager {

    /**
     * Process vehicle entry into the parking lot.
     * @param vehicle the IVehicle entering the lot
     * @return transaction ID for the parked vehicle
     * @throws ParkingException if entry fails
     */
    String handleVehicleEntry(IVehicle vehicle) throws ParkingException;

    /**
     * Process vehicle exit from the parking lot.
     * @param transactionId the transaction ID of the exiting vehicle
     * @return IReceipt for the completed transaction
     * @throws ParkingException if exit fails
     */
    IReceipt handleVehicleExit(String transactionId) throws ParkingException;

    /**
     * Get occupancy rate of the parking lot.
     * @return occupancy percentage (0-100)
     */
    double getOccupancyRate();

    /**
     * Get total number of available parking spots.
     * @return available spot count
     */
    int getAvailableSpots();

    /**
     * Get total number of parking spots in the lot.
     * @return total spot count
     */
    int getTotalSpots();

    /**
     * Check if parking lot is at full capacity.
     * @return true if no spots available, false otherwise
     */
    boolean isAtFullCapacity();

    /**
     * Get total revenue from completed payments.
     * @return total revenue amount
     */
    double getTotalRevenue();

    /**
     * Get count of active (in-progress) parking transactions.
     * @return count of active transactions
     */
    int getActiveTransactionCount();

    /**
     * Get count of completed transactions.
     * @return count of completed transactions
     */
    int getCompletedTransactionCount();

    /**
     * Get system status/statistics.
     * @return formatted status string
     */
    String getSystemStatus();

    /**
     * Get current allocation strategy name.
     * @return strategy name
     */
    String getAllocationStrategy();

    /**
     * Get current fee calculation strategy name.
     * @return strategy name
     */
    String getFeeCalculationStrategy();
}
