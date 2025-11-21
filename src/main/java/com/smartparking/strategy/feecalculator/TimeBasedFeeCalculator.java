package com.smartparking.strategy.feecalculator;

import com.smartparking.core.entity.IVehicleType;
import com.smartparking.constant.VehicleSize;
import com.smartparking.exception.ParkingException;

/**
 * Time-based fee calculation strategy.
 * Applies different rates based on time of day (peak vs off-peak hours).
 * Peak hours (9 AM - 6 PM) charge premium rates.
 * Off-peak hours (6 PM - 9 AM) charge discounted rates.
 * 
 * Peak multiplier: 1.5x (50% premium during peak)
 * Off-peak multiplier: 0.75x (25% discount during off-peak)
 * 
 * Useful for managing parking demand during busy times and incentivizing
 * parking during off-peak hours.
 * 
 * Throws ParkingException for null vehicleType or invalid duration.
 */
public class TimeBasedFeeCalculator implements IFeeCalculator {

    private static final double PEAK_HOUR_MULTIPLIER = 1.5;      // 50% premium during peak
    private static final double OFF_PEAK_HOUR_MULTIPLIER = 0.75; // 25% discount during off-peak

    private static final double SMALL_BASE_RATE = 1.0;
    private static final double MEDIUM_BASE_RATE = 2.0;
    private static final double LARGE_BASE_RATE = 3.0;
    private static final double EXTRA_LARGE_BASE_RATE = 5.0;

    private static final int PEAK_START_HOUR = 9;   // 9 AM
    private static final int PEAK_END_HOUR = 18;    // 6 PM

    private static final int MINUTES_PER_HOUR = 60;

    private static final String STRATEGY_NAME = "Time Based Fee Calculator";
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

        double baseRate = getBaseRateBySize(vehicleType.getSize());
        
        // Simplified approach: assume 50% of parking duration occurs during peak hours
        // In a production system, this would track actual entry/exit times
        int peakMinutes = durationInMinutes / 2;
        int offPeakMinutes = durationInMinutes - peakMinutes;

        double peakHours = (double) peakMinutes / MINUTES_PER_HOUR;
        double offPeakHours = (double) offPeakMinutes / MINUTES_PER_HOUR;

        double peakFee = peakHours * baseRate * PEAK_HOUR_MULTIPLIER;
        double offPeakFee = offPeakHours * baseRate * OFF_PEAK_HOUR_MULTIPLIER;

        double totalFee = peakFee + offPeakFee;
        return Math.ceil(totalFee * 100) / 100.0; // Round to 2 decimals
    }

    /**
     * Get base hourly rate based on vehicle size.
     */
    private double getBaseRateBySize(VehicleSize size) {
        switch (size) {
            case SMALL:
                return SMALL_BASE_RATE;
            case MEDIUM:
                return MEDIUM_BASE_RATE;
            case LARGE:
                return LARGE_BASE_RATE;
            case EXTRA_LARGE:
                return EXTRA_LARGE_BASE_RATE;
            default:
                return MEDIUM_BASE_RATE;
        }
    }

    /**
     * Check if a given hour is during peak hours.
     * Peak hours: 9 AM (9) to 6 PM (18)
     */
    private boolean isPeakHour(int hourOfDay) {
        return hourOfDay >= PEAK_START_HOUR && hourOfDay < PEAK_END_HOUR;
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
