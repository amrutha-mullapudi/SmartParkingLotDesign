package com.smartparking.constant;

/**
 * Enumeration representing the current status of a parking spot.
 */
public enum SpotStatus {
    AVAILABLE("Available"),       // Spot is empty and can be used
    OCCUPIED("Occupied"),         // Spot has a vehicle parked
    RESERVED("Reserved"),         // Spot is reserved for future use
    MAINTENANCE("Maintenance");   // Spot is under maintenance and unavailable

    private final String displayName;

    SpotStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
