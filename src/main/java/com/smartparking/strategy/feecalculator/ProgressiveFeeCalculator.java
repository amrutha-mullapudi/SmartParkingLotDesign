package com.smartparking.strategy.feecalculator;

import com.smartparking.core.entity.IVehicleType;
import com.smartparking.constant.VehicleSize;
import com.smartparking.exception.ParkingException;

/**
 * Progressive fee calculation strategy.
 * Increases hourly rate as parking duration increases (incentivizes shorter stays).
 * Rate increases at specific time thresholds.
 * 
 * Rate structure (per hour):
 * - First 1 hour: Base rate (1x)
 * - 1-3 hours: Base rate * 1.5
 * - 3-6 hours: Base rate * 2.0
 * - Beyond 6 hours: Base rate * 2.5
 * 
 * This encourages quick turnover of parking spaces and discourages long-term parking
 * in premium parking facilities. Revenue increases with parking duration.
 * 
 * Throws ParkingException for null vehicleType or invalid duration.
 */
public class ProgressiveFeeCalculator implements IFeeCalculator {

    private static final double SMALL_BASE_RATE = 1.0;
    private static final double MEDIUM_BASE_RATE = 2.0;
    private static final double LARGE_BASE_RATE = 3.0;
    private static final double EXTRA_LARGE_BASE_RATE = 5.0;

    private static final int TIER_1_HOURS = 1;
    private static final int TIER_2_HOURS = 3;
    private static final int TIER_3_HOURS = 6;

    private static final double TIER_1_MULTIPLIER = 1.0;
    private static final double TIER_2_MULTIPLIER = 1.5;
    private static final double TIER_3_MULTIPLIER = 2.0;
    private static final double TIER_4_MULTIPLIER = 2.5;

    private static final String STRATEGY_NAME = "Progressive Fee Calculator";
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
        int durationInHours = (int) Math.ceil((double) durationInMinutes / 60);

        double multiplier = getMultiplierByDuration(durationInHours);
        double fee = durationInHours * baseRate * multiplier;

        // Round to 2 decimals
        return Math.ceil(fee * 100) / 100.0;
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
     * Get rate multiplier based on parking duration.
     * Encourages shorter parking by penalizing longer durations.
     */
    private double getMultiplierByDuration(int durationInHours) {
        if (durationInHours <= TIER_1_HOURS) {
            return TIER_1_MULTIPLIER;
        } else if (durationInHours <= TIER_2_HOURS) {
            return TIER_2_MULTIPLIER;
        } else if (durationInHours <= TIER_3_HOURS) {
            return TIER_3_MULTIPLIER;
        } else {
            return TIER_4_MULTIPLIER;
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
