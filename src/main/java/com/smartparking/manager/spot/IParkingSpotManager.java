package com.smartparking.manager.spot;

import com.smartparking.core.entity.IParkingSpot;
import com.smartparking.core.entity.IVehicleType;
import com.smartparking.core.entity.IFloor;
import com.smartparking.exception.TransactionException;
import com.smartparking.exception.ParkingException;

/**
 * Interface for managing parking spot allocation and availability in the parking lot.
 * Handles finding available spots, allocating spots to vehicles, and freeing spots.
 */
public interface IParkingSpotManager {

    /**
     * Find an available parking spot for a vehicle.
     * Queries floors for eligible spots and uses allocation strategy to select best spot.
     * @param vehicleType the IVehicleType of the vehicle seeking parking
     * @return the best available IParkingSpot, or null if no spot available
     */
    IParkingSpot findAvailableSpot(IVehicleType vehicleType);

    /**
     * Find consecutive parking spots for multi-spot vehicles (buses, trucks).
     * @param vehicleType the IVehicleType needing consecutive spots
     * @param count number of consecutive spots required
     * @return array of consecutive IParkingSpot objects, empty array if not available
     */
    IParkingSpot[] findConsecutiveSpots(IVehicleType vehicleType, int count);

    /**
     * Allocate (occupy) a parking spot for a vehicle.
     * @param spotId the unique identifier of the spot to allocate
     * @param vehicleId the unique identifier of the vehicle
     * @return true if allocation successful
     * @throws TransactionException if allocation fails
     */
    boolean allocateSpot(String spotId, String vehicleId) 
            throws TransactionException;

    /**
     * Release (vacate) a parking spot.
     * @param spotId the unique identifier of the spot to release
     * @return the vehicle ID that was vacated
     * @throws TransactionException if release fails or spot is empty
     */
    String releaseSpot(String spotId) 
            throws TransactionException;

    /**
     * Get total number of parking spots in the lot.
     * @return total spot count
     */
    int getTotalSpots();

    /**
     * Get number of currently available parking spots.
     * @return available spot count
     */
    int getAvailableSpotsCount();

    /**
     * Get occupancy rate of the parking lot.
     * @return occupancy percentage (0-100)
     */
    double getOccupancyRate();

    /**
     * Check if the parking lot is at full capacity.
     * @return true if no spots available, false otherwise
     */
    boolean isAtFullCapacity();

    /**
     * Register a floor with this manager.
     * @param floor the IFloor to register
     * @return true if registration successful
     * @throws ParkingException if floor already registered
     */
    boolean registerFloor(IFloor floor) 
            throws ParkingException;

    /**
     * Get a floor by floor number.
     * @param floorNumber the floor number (0-indexed)
     * @return the IFloor if found, null otherwise
     */
    IFloor getFloor(int floorNumber);

    /**
     * Get total number of floors managed.
     * @return floor count
     */
    int getFloorCount();
}
