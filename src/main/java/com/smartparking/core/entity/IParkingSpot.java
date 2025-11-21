package com.smartparking.core.entity;

import com.smartparking.constant.SpotSize;
import com.smartparking.constant.SpotStatus;

/**
 * Interface defining the contract for a parking spot in the parking lot system.
 * Each spot represents a single parking space that can accommodate one vehicle.
 * Spots are atomic units - they handle their own availability and compatibility checks.
 */
public interface IParkingSpot {

    /**
     * Get the unique identifier for this parking spot.
     * @return unique spot ID (e.g., "F0-S1" for Floor 0, Spot 1)
     */
    String getSpotId();

    /**
     * Get the floor number where this spot is located.
     * @return floor number (0-indexed)
     */
    int getFloorNumber();

    /**
     * Get the size category of this parking spot.
     * @return the SpotSize of this spot
     */
    SpotSize getSpotSize();

    /**
     * Get the maximum weight capacity of this spot in kilograms.
     * @return weight capacity in kg
     */
    int getMaxWeightCapacity();

    /**
     * Get the current status of this parking spot.
     * @return the SpotStatus enum value
     */
    SpotStatus getStatus();

    /**
     * Check if this spot is currently available for parking.
     * A spot is available if its status is AVAILABLE and no vehicle is occupying it.
     * @return true if spot is available, false otherwise
     */
    boolean isAvailable();

    /**
     * Get the ID of the vehicle currently occupying this spot.
     * @return vehicle ID if spot is occupied, null if spot is empty
     */
    String getCurrentVehicleId();

    /**
     * Check if this spot can accommodate a given vehicle type.
     * Validates spot size compatibility and weight capacity.
     * Note: Does NOT check floor eligibility of the vehicle - that's the floor's responsibility.
     * @param vehicleType the IVehicleType to check compatibility with
     * @return true if this spot can accommodate the vehicle, false otherwise
     */
    boolean canAccommodate(IVehicleType vehicleType);

    /**
     * Occupy this spot with a vehicle.
     * Updates spot status to OCCUPIED and stores the vehicle ID.
     * Should only be called after canAccommodate() returns true.
     * @param vehicleId the ID of the vehicle occupying this spot
     * @return true if occupation was successful, false otherwise
     */
    boolean occupy(String vehicleId);

    /**
     * Vacate this spot, removing the parked vehicle.
     * Updates spot status back to AVAILABLE and clears the vehicle ID.
     * @return the vehicle ID that was vacated, null if spot was already empty
     */
    String vacate();

    /**
     * Set the status of this spot (e.g., for maintenance).
     * @param status the new SpotStatus
     */
    void setStatus(SpotStatus status);
}
