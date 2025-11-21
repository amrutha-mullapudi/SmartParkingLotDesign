package com.smartparking.helper;

import com.smartparking.core.entity.IVehicleType;

/**
 * Context object for pricing rule application.
 * Contains all relevant information for making pricing decisions.
 */
public class PricingContext {

    private final IVehicleType vehicleType;
    private final int durationInMinutes;
    private final long entryTime;
    private final long exitTime;

    /**
     * Constructor for PricingContext.
     * @param vehicleType the vehicle type being charged
     * @param durationInMinutes parking duration
     * @param entryTime entry timestamp
     * @param exitTime exit timestamp
     */
    public PricingContext(IVehicleType vehicleType, int durationInMinutes, long entryTime, long exitTime) {
        this.vehicleType = vehicleType;
        this.durationInMinutes = durationInMinutes;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }

    public IVehicleType getVehicleType() {
        return vehicleType;
    }

    public int getDurationInMinutes() {
        return durationInMinutes;
    }

    public long getEntryTime() {
        return entryTime;
    }

    public long getExitTime() {
        return exitTime;
    }

    /**
     * Get entry hour of day (0-23).
     */
    public int getEntryHourOfDay() {
        return (int) ((entryTime / (1000 * 60 * 60)) % 24);
    }

    /**
     * Get exit hour of day (0-23).
     */
    public int getExitHourOfDay() {
        return (int) ((exitTime / (1000 * 60 * 60)) % 24);
    }

    @Override
    public String toString() {
        return "PricingContext{" +
                "vehicleType=" + vehicleType.getDisplayName() +
                ", durationInMinutes=" + durationInMinutes +
                '}';
    }
}
