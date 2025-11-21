package com.smartparking.manager.registry;

import com.smartparking.core.entity.IVehicleType;
import com.smartparking.exception.ParkingException;

/**
 * Interface for managing vehicle type definitions in the parking lot system.
 * Acts as a registry/factory for vehicle types.
 * Supports dynamic registration of new vehicle types at runtime.
 */
public interface IVehicleTypeRegistry {

    /**
     * Register a new vehicle type in the system.
     * @param vehicleType the IVehicleType to register
     * @return true if registration successful
     * @throws ParkingException if registration fails (e.g., duplicate type ID)
     */
    boolean registerVehicleType(IVehicleType vehicleType) throws ParkingException;

    /**
     * Get a registered vehicle type by its ID.
     * @param vehicleTypeId the unique identifier of the vehicle type
     * @return the IVehicleType if found, null otherwise
     */
    IVehicleType getVehicleType(String vehicleTypeId);

    /**
     * Check if a vehicle type is registered.
     * @param vehicleTypeId the unique identifier to check
     * @return true if type is registered, false otherwise
     */
    boolean isVehicleTypeRegistered(String vehicleTypeId);

    /**
     * Get all registered vehicle types.
     * @return array of all registered IVehicleType objects
     */
    IVehicleType[] getAllVehicleTypes();

    /**
     * Unregister a vehicle type from the system.
     * @param vehicleTypeId the unique identifier of the type to remove
     * @return true if unregistration successful
     * @throws ParkingException if type not found
     */
    boolean unregisterVehicleType(String vehicleTypeId) throws ParkingException;

    /**
     * Get the count of registered vehicle types.
     * @return number of registered types
     */
    int getRegisteredTypesCount();
}
