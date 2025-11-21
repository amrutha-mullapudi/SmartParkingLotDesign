package com.smartparking.constant;

/**
 * Enumeration representing different sizes of parking spots.
 * Maps to vehicle sizes for allocation matching.
 */
public enum SpotSize {
    SMALL("Small"),        // For bikes and scooters
    MEDIUM("Medium"),      // For cars and sedans
    LARGE("Large"),        // For SUVs and vans
    EXTRA_LARGE("ExtraLarge"); // For buses and trucks

    private final String displayName;

    SpotSize(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Check if a spot size can accommodate a vehicle size.
     * @param vehicleSize the size of the vehicle
     * @return true if this spot size can fit the vehicle
     */
    public boolean canAccommodateVehicleSize(VehicleSize vehicleSize) {
        return this.ordinal() >= vehicleSize.ordinal();
    }
}
