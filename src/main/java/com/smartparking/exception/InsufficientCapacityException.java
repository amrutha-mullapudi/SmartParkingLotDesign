package com.smartparking.exception;

/**
 * Exception thrown when parking lot capacity is insufficient for a vehicle.
 */
public class InsufficientCapacityException extends ParkingException {

    private static final long serialVersionUID = 1L;
    private static final String ERROR_CODE = "INSUFFICIENT_CAPACITY";

    /**
     * Constructor with capacity information.
     * @param required required capacity/spots
     * @param available available capacity/spots
     */
    public InsufficientCapacityException(int required, int available) {
        super("Insufficient capacity. Required: " + required + ", Available: " + available, ERROR_CODE);
    }

    /**
     * Constructor with custom message.
     * @param message detailed error message
     */
    public InsufficientCapacityException(String message) {
        super(message, ERROR_CODE);
    }

    /**
     * Constructor with custom message and cause.
     * @param message detailed error message
     * @param cause the underlying exception
     */
    public InsufficientCapacityException(String message, Throwable cause) {
        super(message, ERROR_CODE, cause);
    }
}
