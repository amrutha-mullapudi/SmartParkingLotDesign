package com.smartparking.constant;

/**
 * Enumeration representing different weight categories of vehicles in the parking lot system.
 * Used for floor weight capacity management and allocation.
 */
public enum VehicleWeight {
    LIGHT("Light", 500),       // Bikes, scooters (< 500kg)
    MEDIUM("Medium", 1500),    // Cars, sedans (500-1500kg)
    HEAVY("Heavy", 3000);      // Buses, trucks (> 1500kg)

    private final String displayName;
    private final int maxWeightKg;

    VehicleWeight(String displayName, int maxWeightKg) {
        this.displayName = displayName;
        this.maxWeightKg = maxWeightKg;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getMaxWeightKg() {
        return maxWeightKg;
    }
}
