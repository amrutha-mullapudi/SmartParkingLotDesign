package com.smartparking.exception;

/**
 * Exception thrown when payment processing fails.
 */
public class PaymentFailedException extends ParkingException {

    private static final long serialVersionUID = 1L;
    private static final String ERROR_CODE = "PAYMENT_FAILED";

    /**
     * Constructor with payment details.
     * @param paymentId the payment that failed
     * @param reason reason for failure
     */
    public PaymentFailedException(String paymentId, String reason) {
        super("Payment failed for payment ID: " + paymentId + ". Reason: " + reason, ERROR_CODE);
    }

    /**
     * Constructor with custom message.
     * @param message detailed error message
     */
    public PaymentFailedException(String message) {
        super(message, ERROR_CODE);
    }

    /**
     * Constructor with custom message and cause.
     * @param message detailed error message
     * @param cause the underlying exception
     */
    public PaymentFailedException(String message, Throwable cause) {
        super(message, ERROR_CODE, cause);
    }
}
