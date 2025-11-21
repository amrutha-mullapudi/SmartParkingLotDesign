package com.smartparking.strategy.allocator;

import com.smartparking.core.entity.IParkingSpot;
import com.smartparking.exception.ParkingException;

/**
 * Nearest-to-exit spot allocation strategy.
 * Allocates spots closest to the parking lot exit for faster vehicle departure.
 * Useful for reducing congestion and improving exit flow.
 * 
 * Strategy: Among available spots, prefer spots with higher spot IDs/numbers
 * (assuming spots are numbered in increasing order towards the exit).
 * 
 * Throws ParkingException for null or empty input arrays.
 */
public class NearestToExitAllocator implements ISpotAllocator {

    private static final String STRATEGY_NAME = "Nearest to Exit Allocator";
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

        // Select the spot with the highest ID (nearest to exit)
        // Assumes spot IDs follow a pattern where higher numbers = closer to exit
        IParkingSpot selectedSpot = availableSpots[0];

        for (IParkingSpot spot : availableSpots) {
            // Compare spot IDs lexicographically
            // e.g., "F0-S10" > "F0-S9" because "S10" > "S9" as strings
            if (spot.getSpotId().compareTo(selectedSpot.getSpotId()) > 0) {
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

        // For consecutive spots, prefer the group starting with highest spot ID
        // Nearest to exit takes precedence
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
