package com.smartparking.manager.transaction;

import com.smartparking.core.entity.ITransaction;
import com.smartparking.exception.TransactionException;

/**
 * Interface for managing parking transactions in the system.
 * Handles creation, tracking, and retrieval of parking sessions.
 */
public interface ITransactionManager {

    /**
     * Create a new parking transaction when a vehicle enters the lot.
     * @param vehicleId unique identifier of the vehicle
     * @param spotIds array of parking spot IDs occupied by the vehicle
     * @param entryTime timestamp when vehicle entered (milliseconds)
     * @return the created ITransaction
     * @throws TransactionException if transaction creation fails
     */
    ITransaction createTransaction(String vehicleId, String[] spotIds, long entryTime) 
            throws TransactionException;

    /**
     * Get an active transaction by its ID.
     * @param transactionId the unique identifier of the transaction
     * @return the ITransaction if found
     * @throws TransactionException if transaction not found
     */
    ITransaction getTransaction(String transactionId) 
            throws TransactionException;

    /**
     * Record vehicle exit and calculate parking fee.
     * @param transactionId the transaction ID
     * @param exitTime timestamp when vehicle exited (milliseconds)
     * @return true if exit recorded successfully
     * @throws TransactionException if operation fails
     */
    boolean recordExit(String transactionId, long exitTime) 
            throws TransactionException;

    /**
     * Get all active (in-progress) transactions.
     * @return array of active ITransaction objects
     */
    ITransaction[] getActiveTransactions();

    /**
     * Get all completed transactions.
     * @return array of completed ITransaction objects
     */
    ITransaction[] getCompletedTransactions();

    /**
     * Get transactions for a specific vehicle.
     * @param vehicleId the vehicle ID to search for
     * @return array of ITransaction objects for this vehicle
     */
    ITransaction[] getVehicleTransactions(String vehicleId);

    /**
     * Get transaction count (all statuses).
     * @return total transaction count
     */
    int getTransactionCount();

    /**
     * Get count of active transactions.
     * @return count of in-progress transactions
     */
    int getActiveTransactionCount();

    /**
     * Mark a transaction as complete (after payment processed).
     * @param transactionId the transaction ID to complete
     * @return true if completion successful
     * @throws TransactionException if transaction not found or operation fails
     */
    boolean completeTransaction(String transactionId) 
            throws TransactionException;
}
