package com.smartparking.core.entity;

import com.smartparking.constant.VehicleSize;
import com.smartparking.constant.VehicleWeight;
import com.smartparking.constant.SpotSize;

/**
 * Interface defining the contract for vehicle type definitions in the parking lot system.
 * This represents the abstract definition of a vehicle type (e.g., Bike, Car, Bus, Truck).
 * Multiple vehicle instances can share the same vehicle type definition.
 */
public interface IVehicleType {

    /**
     * Get the unique identifier for this vehicle type.
     * @return unique vehicle type ID
     */
    String getVehicleTypeId();

    /**
     * Get the size category of this vehicle type.
     * @return the VehicleSize enum value
     */
    VehicleSize getSize();

    /**
     * Get the weight category of this vehicle type.
     * @return the VehicleWeight enum value
     */
    VehicleWeight getWeightCategory();

    /**
     * Get the weight in kilograms for this vehicle type.
     * Used for floor capacity calculations.
     * @return weight in kg
     */
    int getWeightInKg();

    /**
     * Get the number of parking spots required for this vehicle type.
     * Most vehicles need 1 spot, but large vehicles (buses, trucks) may need multiple consecutive spots.
     * @return number of spots required (typically 1-4)
     */
    int getSpotsRequired();

    /**
     * Get the spot size requirement for this vehicle type.
     * @return the SpotSize that this vehicle type needs
     */
    SpotSize getSpotSizeRequirement();

    /**
     * Get the list of floor numbers (0-indexed) where this vehicle type can be parked.
     * For example, buses might only be allowed on ground floor (floor 0).
     * @return array of eligible floor numbers
     */
    int[] getFloorEligibility();

    /**
     * Check if this vehicle type can fit in a parking spot of given size.
     * @param spotSize the size of the parking spot to check
     * @return true if this vehicle can fit, false otherwise
     */
    boolean canFitInSpot(SpotSize spotSize);

    /**
     * Check if this vehicle type is allowed to park on a given floor.
     * @param floorNumber the floor number to check (0-indexed)
     * @return true if parking on this floor is allowed, false otherwise
     */
    boolean canParkOnFloor(int floorNumber);

    /**
     * Get a display name or description for this vehicle type.
     * @return human-readable name (e.g., "Bike", "Car", "Bus")
     */
    String getDisplayName();
}
