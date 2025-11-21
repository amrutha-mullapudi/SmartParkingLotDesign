package com.smartparking.exception;

/**
 * Exception thrown when no parking spots are available for a vehicle.
 */
public class NoSpotAvailableException extends ParkingException {

    private static final long serialVersionUID = 1L;
    private static final String ERROR_CODE = "NO_SPOT_AVAILABLE";

    /**
     * Constructor with vehicle type information.
     * @param vehicleType the type of vehicle that couldn't find a spot
     */
    public NoSpotAvailableException(String vehicleType) {
        super("No parking spot available for vehicle type: " + vehicleType, ERROR_CODE);
    }

    /**
     * Constructor with custom message.
     * @param message detailed error message
     */
    public NoSpotAvailableException(String message, Throwable cause) {
        super(message, ERROR_CODE, cause);
    }
}
