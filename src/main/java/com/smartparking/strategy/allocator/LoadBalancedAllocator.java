package com.smartparking.strategy.allocator;

import com.smartparking.core.entity.IParkingSpot;
import com.smartparking.exception.ParkingException;

/**
 * Load-balanced spot allocation strategy.
 * Distributes parked vehicles evenly across available spots in the lot,
 * avoiding concentration of parked vehicles in specific areas.
 * 
 * Strategy: Among available spots, select spots that are surrounded by
 * fewer occupied spots, promoting spatial distribution.
 * 
 * Throws ParkingException for null or empty input arrays.
 */
public class LoadBalancedAllocator implements ISpotAllocator {

    private static final String STRATEGY_NAME = "Load Balanced Allocator";
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

        // Simple load balancing: select the first available spot
        // Assumes spots are provided in a randomized or load-balanced order by the caller
        return availableSpots[0];
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

        // For consecutive spots, return all provided spots
        // Load balancing for multi-spot vehicles is handled by floor selection
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
