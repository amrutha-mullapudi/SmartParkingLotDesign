package com.smartparking.exception;

/**
 * Base exception class for all parking lot system exceptions.
 * All custom exceptions in the system should extend this class.
 */
public class ParkingException extends Exception {

    private static final long serialVersionUID = 1L;
    private final String errorCode;

    /**
     * Constructor with message.
     * @param message description of the error
     */
    public ParkingException(String message) {
        super(message);
        this.errorCode = "PARKING_ERROR";
    }

    /**
     * Constructor with message and error code.
     * @param message description of the error
     * @param errorCode specific error code
     */
    public ParkingException(String message, String errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    /**
     * Constructor with message and cause.
     * @param message description of the error
     * @param cause the underlying exception
     */
    public ParkingException(String message, Throwable cause) {
        super(message, cause);
        this.errorCode = "PARKING_ERROR";
    }

    /**
     * Constructor with message, error code, and cause.
     * @param message description of the error
     * @param errorCode specific error code
     * @param cause the underlying exception
     */
    public ParkingException(String message, String errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    /**
     * Get the error code for this exception.
     * @return error code string
     */
    public String getErrorCode() {
        return errorCode;
    }

    @Override
    public String toString() {
        return "ParkingException{" +
                "errorCode='" + errorCode + '\'' +
                ", message='" + getMessage() + '\'' +
                '}';
    }
}
