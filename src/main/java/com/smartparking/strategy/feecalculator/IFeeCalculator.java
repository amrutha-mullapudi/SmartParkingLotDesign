package com.smartparking.strategy.feecalculator;

import com.smartparking.core.entity.IVehicleType;
import com.smartparking.exception.ParkingException;

/**
 * Interface defining the contract for fee calculation strategies in the parking lot system.
 * Different implementations provide different pricing models (flat rate, progressive, time-based, etc.).
 * This enables runtime strategy switching without changing core code.
 * 
 * All methods throw ParkingException when given invalid inputs (null or invalid duration).
 */
public interface IFeeCalculator {

    /**
     * Calculate the parking fee based on parking duration and vehicle type.
     * 
     * @param durationInMinutes the total parking duration in minutes (must be positive)
     * @param vehicleType the IVehicleType of the parked vehicle (must not be null)
     * @return calculated parking fee amount
     * @throws ParkingException if vehicleType is null or durationInMinutes <= 0
     */
    double calculateFee(int durationInMinutes, IVehicleType vehicleType) throws ParkingException;

    /**
     * Get the name/description of this fee calculation strategy.
     * 
     * @return strategy name (e.g., "Standard Fee", "Progressive Fee")
     */
    String getStrategyName();
}
