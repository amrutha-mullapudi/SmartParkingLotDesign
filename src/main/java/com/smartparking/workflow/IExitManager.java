package com.smartparking.workflow;

import com.smartparking.helper.IReceipt;
import com.smartparking.exception.ParkingException;

/**
 * Interface for vehicle exit workflow.
 * Handles the process of a vehicle leaving the parking lot and payment.
 */
public interface IExitManager {

    /**
     * Process vehicle exit from the parking lot.
     * Records exit time, calculates fee, processes payment.
     * @param transactionId the ID of the transaction to process
     * @return IReceipt for the completed transaction
     * @throws ParkingException if exit process fails
     */
    IReceipt processExit(String transactionId) throws ParkingException;

    /**
     * Get exit status/statistics.
     * @return formatted string with exit information
     */
    String getExitStats();
}
