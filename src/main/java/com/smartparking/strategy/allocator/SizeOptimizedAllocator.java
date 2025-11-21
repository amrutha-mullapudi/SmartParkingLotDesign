package com.smartparking.strategy.allocator;

import com.smartparking.core.entity.IParkingSpot;
import com.smartparking.constant.SpotStatus;
import com.smartparking.exception.ParkingException;

/**
 * Size-optimized spot allocation strategy.
 * Allocates spots based on optimal fit between vehicle and spot size.
 * Prefers to avoid assigning larger spots to smaller vehicles, preserving
 * larger spots for vehicles that truly need them.
 * 
 * Strategy: Among available spots, prefer the spot with the smallest
 * adequate size that can accommodate the vehicle, saving larger spots for larger vehicles.
 * 
 * Throws ParkingException for null or empty input arrays.
 */
public class SizeOptimizedAllocator implements ISpotAllocator {

    private static final String STRATEGY_NAME = "Size Optimized Allocator";
    private static final String ERROR_CODE_INVALID_SPOTS = "ERR_ALLOCATOR_INVALID_SPOTS";

    @Override
    public IParkingSpot allocateSpot(IParkingSpot[] availableSpots) throws ParkingException {
        if (availableSpots == null || availableSpots.length == 0) {
            throw new ParkingException(
                ERROR_CODE_INVALID_SPOTS,
                "Cannot allocate spot: Available spots array is null or empty",
                STRATEGY_NAME
            );
        }

        // Find the smallest spot that can accommodate (best fit)
        IParkingSpot selectedSpot = availableSpots[0];

        for (IParkingSpot spot : availableSpots) {
            // Prefer spot with smaller size (best fit) if both available
            if (spot.getSpotSize().ordinal() < selectedSpot.getSpotSize().ordinal()) {
                selectedSpot = spot;
            }
        }

        return selectedSpot;
    }

    @Override
    public IParkingSpot[] allocateConsecutiveSpots(IParkingSpot[] consecutiveSpots) throws ParkingException {
        if (consecutiveSpots == null || consecutiveSpots.length == 0) {
            throw new ParkingException(
                ERROR_CODE_INVALID_SPOTS,
                "Cannot allocate consecutive spots: Input array is null or empty",
                STRATEGY_NAME
            );
        }

        // For consecutive spots, size optimization is less critical
        // Return all provided consecutive spots
        return consecutiveSpots;
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
