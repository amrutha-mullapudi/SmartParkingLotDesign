package com.smartparking.exception;

/**
 * Exception thrown when a transaction operation fails.
 */
public class TransactionException extends ParkingException {

    private static final long serialVersionUID = 1L;
    private static final String ERROR_CODE = "TRANSACTION_ERROR";

    /**
     * Constructor with transaction details.
     * @param transactionId the transaction with error
     * @param operation the operation that failed
     */
    public TransactionException(String transactionId, String operation) {
        super("Transaction error for ID: " + transactionId + ". Operation: " + operation, ERROR_CODE);
    }

    /**
     * Constructor with custom message.
     * @param message detailed error message
     */
    public TransactionException(String message) {
        super(message, ERROR_CODE);
    }

    /**
     * Constructor with custom message and cause.
     * @param message detailed error message
     * @param cause the underlying exception
     */
    public TransactionException(String message, Throwable cause) {
        super(message, ERROR_CODE, cause);
    }
}
