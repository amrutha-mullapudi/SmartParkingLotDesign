package com.smartparking.manager.transaction;

import com.smartparking.core.entity.ITransaction;
import com.smartparking.core.impl.Transaction;
import com.smartparking.constant.TransactionStatus;
import com.smartparking.exception.TransactionException;
import java.util.HashMap;
import java.util.Map;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Implementation of ITransactionManager.
 * Manages the lifecycle of parking transactions from entry to exit to completion.
 */
public class TransactionManager implements ITransactionManager {

    private final Map<String, ITransaction> allTransactions;

    /**
     * Constructor for TransactionManager.
     */
    public TransactionManager() {
        this.allTransactions = new HashMap<>();
    }

    @Override
    public ITransaction createTransaction(String vehicleId, String[] spotIds, long entryTime) 
            throws TransactionException {
        if (vehicleId == null || vehicleId.isEmpty()) {
            throw new TransactionException("Vehicle ID cannot be null or empty");
        }
        
        if (spotIds == null || spotIds.length == 0) {
            throw new TransactionException("Spot IDs array cannot be null or empty");
        }
        
        if (entryTime <= 0) {
            throw new TransactionException("Entry time must be greater than 0");
        }

        String transactionId = generateTransactionId();
        ITransaction transaction = new Transaction(transactionId, vehicleId, spotIds, entryTime);
        allTransactions.put(transactionId, transaction);

        return transaction;
    }

    @Override
    public ITransaction getTransaction(String transactionId) 
            throws TransactionException {
        if (transactionId == null || transactionId.isEmpty()) {
            throw new TransactionException("Transaction ID cannot be null or empty");
        }
        
        ITransaction transaction = allTransactions.get(transactionId);
        if (transaction == null) {
            throw new TransactionException(transactionId, "Transaction not found");
        }
        
        return transaction;
    }

    @Override
    public boolean recordExit(String transactionId, long exitTime) 
            throws TransactionException {
        if (transactionId == null || transactionId.isEmpty()) {
            throw new TransactionException("Transaction ID cannot be null or empty");
        }
        
        if (exitTime <= 0) {
            throw new TransactionException(transactionId, "Exit time must be greater than 0");
        }

        ITransaction transaction = allTransactions.get(transactionId);
        if (transaction == null) {
            throw new TransactionException(transactionId, "Transaction not found");
        }

        return transaction.recordExit(exitTime);
    }

    @Override
    public ITransaction[] getActiveTransactions() {
        List<ITransaction> activeList = new ArrayList<>();
        for (ITransaction transaction : allTransactions.values()) {
            if (transaction.getStatus() == TransactionStatus.IN_PROGRESS) {
                activeList.add(transaction);
            }
        }
        return activeList.toArray(new ITransaction[0]);
    }

    @Override
    public ITransaction[] getCompletedTransactions() {
        List<ITransaction> completedList = new ArrayList<>();
        for (ITransaction transaction : allTransactions.values()) {
            if (transaction.getStatus() != TransactionStatus.IN_PROGRESS) {
                completedList.add(transaction);
            }
        }
        return completedList.toArray(new ITransaction[0]);
    }

    @Override
    public ITransaction[] getVehicleTransactions(String vehicleId) {
        if (vehicleId == null) {
            return new ITransaction[0];
        }

        List<ITransaction> vehicleTransactionsList = new ArrayList<>();
        for (ITransaction transaction : allTransactions.values()) {
            if (transaction.getVehicleId().equals(vehicleId)) {
                vehicleTransactionsList.add(transaction);
            }
        }
        return vehicleTransactionsList.toArray(new ITransaction[0]);
    }

    @Override
    public int getTransactionCount() {
        return allTransactions.size();
    }

    @Override
    public int getActiveTransactionCount() {
        return getActiveTransactions().length;
    }

    @Override
    public boolean completeTransaction(String transactionId) 
            throws TransactionException {
        if (transactionId == null || transactionId.isEmpty()) {
            throw new TransactionException("Transaction ID cannot be null or empty");
        }

        ITransaction transaction = allTransactions.get(transactionId);
        if (transaction == null) {
            throw new TransactionException(transactionId, "Transaction not found");
        }

        transaction.complete();
        return true;
    }

    /**
     * Generate a unique transaction ID.
     */
    private String generateTransactionId() {
        return "TXN-" + UUID.randomUUID().toString();
    }

    @Override
    public String toString() {
        return "TransactionManager{" +
                "totalTransactions=" + allTransactions.size() +
                ", activeTransactions=" + getActiveTransactionCount() +
                '}';
    }
}
