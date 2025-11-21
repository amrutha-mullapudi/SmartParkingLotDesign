package com.smartparking.core.entity;

import com.smartparking.constant.SpotSize;

/**
 * Interface defining the contract for a parking floor in the parking lot system.
 * A floor manages multiple parking spots and tracks floor-level constraints
 * (weight capacity, allowed vehicle types, availability).
 * Floors are responsible for querying and filtering spots based on vehicle type requirements.
 */
public interface IFloor {

    /**
     * Get the unique identifier for this floor.
     * @return floor number (0-indexed, e.g., 0 for ground floor)
     */
    int getFloorNumber();

    /**
     * Get the total number of parking spots on this floor.
     * @return total spot count
     */
    int getTotalSpots();

    /**
     * Get the number of currently available spots on this floor.
     * @return count of AVAILABLE spots
     */
    int getAvailableSpotsCount();

    /**
     * Get the current weight load on this floor (sum of weights of all parked vehicles).
     * @return current load in kg
     */
    int getCurrentFloorLoad();

    /**
     * Get the maximum weight capacity for this floor.
     * @return capacity in kg
     */
    int getMaxFloorWeightCapacity();

    /**
     * Check if this floor can accommodate an additional vehicle without exceeding weight capacity.
     * @param vehicleType the IVehicleType to check
     * @return true if adding this vehicle won't exceed capacity, false otherwise
     */
    boolean canAccommodateAdditionalVehicle(IVehicleType vehicleType);

    /**
     * Get all available parking spots on this floor that can accommodate a given vehicle type.
     * Filters spots by:
     * - Spot status (must be AVAILABLE)
     * - Spot size compatibility
     * - Weight capacity
     * Note: Does NOT check floor eligibility - caller must verify vehicle can park on this floor.
     * @param vehicleType the IVehicleType to find compatible spots for
     * @return array of compatible IParkingSpot objects, empty array if none available
     */
    IParkingSpot[] getAvailableSpotsByVehicleType(IVehicleType vehicleType);

    /**
     * Get a specified number of consecutive available parking spots on this floor
     * that can accommodate a given vehicle type.
     * Used for multi-spot vehicles (buses, trucks).
     * @param vehicleType the IVehicleType to find spots for
     * @param consecutiveCount number of consecutive spots needed
     * @return array of consecutive IParkingSpot objects, empty array if not available
     */
    IParkingSpot[] getConsecutiveSpots(IVehicleType vehicleType, int consecutiveCount);

    /**
     * Register a vehicle as parked on this floor.
     * Updates floor's current load.
     * @param vehicleType the IVehicleType of the parked vehicle
     * @return true if registration successful, false otherwise
     */
    boolean registerParkedVehicle(IVehicleType vehicleType);

    /**
     * Unregister a vehicle from this floor.
     * Reduces floor's current load.
     * @param vehicleType the IVehicleType of the vehicle being removed
     * @return true if unregistration successful, false otherwise
     */
    boolean unregisterParkedVehicle(IVehicleType vehicleType);

    /**
     * Get all parking spots on this floor (regardless of status).
     * @return array of all IParkingSpot objects on this floor
     */
    IParkingSpot[] getAllSpots();

    /**
     * Check if this floor is under maintenance.
     * @return true if floor is under maintenance, false otherwise
     */
    boolean isUnderMaintenance();

    /**
     * Set maintenance status for this floor.
     * @param underMaintenance true to mark as under maintenance, false to open
     */
    void setUnderMaintenance(boolean underMaintenance);
}
