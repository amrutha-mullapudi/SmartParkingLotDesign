package com.smartparking.constant;

/**
 * Enumeration representing different sizes of vehicles in the parking lot system.
 * Used for spot allocation and capacity planning.
 */
public enum VehicleSize {
    SMALL("Small"),           // Bikes, scooters (< 2m)
    MEDIUM("Medium"),         // Cars, sedans (2-4.5m)
    LARGE("Large"),           // SUVs, vans (4.5-6m)
    EXTRA_LARGE("ExtraLarge"); // Buses, trucks (> 6m)

    private final String displayName;

    VehicleSize(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
