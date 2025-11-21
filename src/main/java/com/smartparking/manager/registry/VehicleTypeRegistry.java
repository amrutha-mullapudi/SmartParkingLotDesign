package com.smartparking.manager.registry;

import com.smartparking.core.entity.IVehicleType;
import com.smartparking.exception.ParkingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;

/**
 * Implementation of IVehicleTypeRegistry.
 * Manages registration and lookup of vehicle type definitions.
 * Allows runtime addition of new vehicle types without code changes.
 */
public class VehicleTypeRegistry implements IVehicleTypeRegistry {

    private final Map<String, IVehicleType> registeredTypes;

    /**
     * Constructor for VehicleTypeRegistry.
     */
    public VehicleTypeRegistry() {
        this.registeredTypes = new HashMap<>();
    }

    @Override
    public boolean registerVehicleType(IVehicleType vehicleType) throws ParkingException {
        if (vehicleType == null) {
            throw new ParkingException("Vehicle type cannot be null");
        }

        String typeId = vehicleType.getVehicleTypeId();
        if (typeId == null || typeId.isEmpty()) {
            throw new ParkingException("Vehicle type ID cannot be null or empty");
        }

        if (registeredTypes.containsKey(typeId)) {
            throw new ParkingException("Vehicle type already registered with ID: " + typeId);
        }

        registeredTypes.put(typeId, vehicleType);
        return true;
    }

    @Override
    public IVehicleType getVehicleType(String vehicleTypeId) {
        if (vehicleTypeId == null) {
            return null;
        }
        return registeredTypes.get(vehicleTypeId);
    }

    @Override
    public boolean isVehicleTypeRegistered(String vehicleTypeId) {
        if (vehicleTypeId == null) {
            return false;
        }
        return registeredTypes.containsKey(vehicleTypeId);
    }

    @Override
    public IVehicleType[] getAllVehicleTypes() {
        Collection<IVehicleType> types = registeredTypes.values();
        return types.toArray(new IVehicleType[0]);
    }

    @Override
    public boolean unregisterVehicleType(String vehicleTypeId) throws ParkingException {
        if (vehicleTypeId == null || vehicleTypeId.isEmpty()) {
            throw new ParkingException("Vehicle type ID cannot be null or empty");
        }

        if (!registeredTypes.containsKey(vehicleTypeId)) {
            throw new ParkingException("Vehicle type not found: " + vehicleTypeId);
        }

        registeredTypes.remove(vehicleTypeId);
        return true;
    }

    @Override
    public int getRegisteredTypesCount() {
        return registeredTypes.size();
    }

    @Override
    public String toString() {
        return "VehicleTypeRegistry{" +
                "registeredTypes=" + registeredTypes.size() +
                '}';
    }
}
