package com.smartparking.strategy.allocator;

import com.smartparking.core.entity.IParkingSpot;
import com.smartparking.exception.ParkingException;

/**
 * Interface defining the contract for spot allocation strategies in the parking lot system.
 * Different implementations provide different algorithms for selecting the best parking spot
 * from available options. This enables runtime strategy switching without changing core code.
 * 
 * All methods throw ParkingException when given invalid inputs (null or empty arrays).
 */
public interface ISpotAllocator {

    /**
     * Allocate (select) the best parking spot from a list of candidates.
     * The selection criteria depends on the specific implementation strategy.
     * 
     * @param availableSpots array of available parking spots to choose from
     * @return the selected IParkingSpot
     * @throws ParkingException if availableSpots is null or empty
     */
    IParkingSpot allocateSpot(IParkingSpot[] availableSpots) throws ParkingException;

    /**
     * Allocate consecutive spots for vehicles that need multiple parking spaces.
     * 
     * @param consecutiveSpots array of consecutive spots available
     * @return array of selected consecutive spots
     * @throws ParkingException if consecutiveSpots is null or empty
     */
    IParkingSpot[] allocateConsecutiveSpots(IParkingSpot[] consecutiveSpots) throws ParkingException;

    /**
     * Get the name/description of this allocation strategy.
     * 
     * @return strategy name (e.g., "Load Balanced", "Size Optimized")
     */
    String getStrategyName();
}
