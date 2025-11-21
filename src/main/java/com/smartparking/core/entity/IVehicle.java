package com.smartparking.core.entity;

/**
 * Interface defining the contract for a vehicle instance in the parking lot system.
 * Each vehicle represents a specific vehicle (identified by license plate) that is parked
 * or seeking parking in the lot. A vehicle instance is associated with a vehicle type definition.
 */
public interface IVehicle {

    /**
     * Get the unique identifier for this vehicle instance.
     * @return unique vehicle ID (typically UUID)
     */
    String getVehicleId();

    /**
     * Get the license plate of this vehicle.
     * @return license plate string
     */
    String getLicensePlate();

    /**
     * Get the vehicle type definition for this vehicle.
     * This is a reference to the IVehicleType that defines this vehicle's properties.
     * @return the IVehicleType definition
     */
    IVehicleType getVehicleType();

    /**
     * Get the name of the vehicle owner.
     * @return owner name
     */
    String getOwnerName();

    /**
     * Get the contact number of the vehicle owner.
     * @return phone number
     */
    String getOwnerContact();

    /**
     * Get the current parking spot(s) where this vehicle is parked.
     * For vehicles requiring multiple spots (buses, trucks), this will be an array.
     * For single-spot vehicles, this will be an array with one element or empty if not parked.
     * @return array of IParkingSpot where vehicle is currently parked, empty array if not parked
     */
    IParkingSpot[] getCurrentSpots();

    /**
     * Get the time when this vehicle entered the parking lot.
     * @return entry timestamp in milliseconds (Unix epoch)
     */
    long getEntryTime();

    /**
     * Set the entry time for this vehicle (when it enters the lot).
     * @param entryTime timestamp in milliseconds
     */
    void setEntryTime(long entryTime);

    /**
     * Park this vehicle at a single parking spot.
     * Validates that the spot can accommodate this vehicle type.
     * @param spot the IParkingSpot to park at
     * @return true if parking was successful, false otherwise
     */
    boolean parkAtSpot(IParkingSpot spot);

    /**
     * Park this vehicle at multiple consecutive parking spots.
     * Used for large vehicles (buses, trucks) that require multiple spots.
     * All spots must be consecutive and compatible before occupation.
     * @param spots array of consecutive IParkingSpot objects
     * @return true if parking was successful, false otherwise
     */
    boolean parkAtConsecutiveSpots(IParkingSpot[] spots);

    /**
     * Unpark this vehicle from its current parking spot(s).
     * Releases all occupied spots and updates vehicle state.
     * @return true if unparking was successful, false otherwise
     */
    boolean unparkFromSpots();

    /**
     * Check if this vehicle is currently parked.
     * @return true if vehicle is parked at one or more spots, false otherwise
     */
    boolean isParked();
}
