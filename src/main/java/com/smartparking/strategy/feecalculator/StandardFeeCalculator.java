package com.smartparking.strategy.feecalculator;

import com.smartparking.core.entity.IVehicleType;
import com.smartparking.constant.VehicleSize;
import com.smartparking.exception.ParkingException;

/**
 * Standard fee calculation strategy.
 * Applies a flat hourly rate based on vehicle size.
 * Larger vehicles pay more per hour than smaller vehicles.
 * 
 * Pricing:
 * - SMALL (Bikes): $1.00/hour
 * - MEDIUM (Cars): $2.00/hour
 * - LARGE (SUVs, Vans): $3.00/hour
 * - EXTRA_LARGE (Buses, Trucks): $5.00/hour
 * 
 * Throws ParkingException for null vehicleType or invalid duration.
 */
public class StandardFeeCalculator implements IFeeCalculator {

    private static final double SMALL_RATE = 1.0;      // $ per hour
    private static final double MEDIUM_RATE = 2.0;     // $ per hour
    private static final double LARGE_RATE = 3.0;      // $ per hour
    private static final double EXTRA_LARGE_RATE = 5.0; // $ per hour
    private static final int MINUTES_PER_HOUR = 60;

    private static final String STRATEGY_NAME = "Standard Fee Calculator";
    private static final String ERROR_CODE_INVALID_VEHICLE = "ERR_CALC_INVALID_VEHICLE";
    private static final String ERROR_CODE_INVALID_DURATION = "ERR_CALC_INVALID_DURATION";

    @Override
    public double calculateFee(int durationInMinutes, IVehicleType vehicleType) throws ParkingException {
        if (vehicleType == null) {
            throw new ParkingException(
                ERROR_CODE_INVALID_VEHICLE,
                "Cannot calculate fee: Vehicle type is null",
                STRATEGY_NAME
            );
        }

        if (durationInMinutes <= 0) {
            throw new ParkingException(
                ERROR_CODE_INVALID_DURATION,
                "Cannot calculate fee: Duration must be positive (got " + durationInMinutes + " minutes)",
                STRATEGY_NAME
            );
        }

        double hourlyRate = getHourlyRateBySize(vehicleType.getSize());
        double durationInHours = (double) durationInMinutes / MINUTES_PER_HOUR;

        return Math.ceil(durationInHours * hourlyRate * 100) / 100.0; // Round to 2 decimals
    }

    /**
     * Get hourly rate based on vehicle size.
     */
    private double getHourlyRateBySize(VehicleSize size) {
        switch (size) {
            case SMALL:
                return SMALL_RATE;
            case MEDIUM:
                return MEDIUM_RATE;
            case LARGE:
                return LARGE_RATE;
            case EXTRA_LARGE:
                return EXTRA_LARGE_RATE;
            default:
                return MEDIUM_RATE; // Default fallback
        }
    }

    @Override
    public String getStrategyName() {
        return STRATEGY_NAME;
    }

    @Override
    public String toString() {
        return getStrategyName();
    }
}
