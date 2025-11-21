package com.smartparking.exception;

/**
 * Exception thrown when vehicle data is invalid or vehicle is not found.
 */
public class InvalidVehicleException extends ParkingException {

    private static final long serialVersionUID = 1L;
    private static final String ERROR_CODE = "INVALID_VEHICLE";

    /**
     * Constructor with vehicle ID.
     * @param vehicleId the invalid vehicle identifier
     */
    public InvalidVehicleException(String vehicleId) {
        super("Invalid vehicle: " + vehicleId, ERROR_CODE);
    }

    /**
     * Constructor with custom message.
     * @param message detailed error message
     */
    public InvalidVehicleException(String message, Throwable cause) {
        super(message, ERROR_CODE, cause);
    }
}
