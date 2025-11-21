package com.smartparking.core.impl;

import com.smartparking.core.entity.IVehicle;
import com.smartparking.core.entity.IVehicleType;
import com.smartparking.core.entity.IParkingSpot;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of IVehicle representing a specific vehicle instance in the parking lot.
 * Each vehicle has a unique license plate and is associated with a vehicle type.
 * A vehicle tracks its parking status and current location.
 */
public class Vehicle implements IVehicle {

    private final String vehicleId;
    private final String licensePlate;
    private final IVehicleType vehicleType;
    private final String ownerName;
    private final String ownerContact;
    private final List<IParkingSpot> currentSpots;
    private long entryTime;
    private boolean isParked;

    /**
     * Constructor for Vehicle.
     * @param vehicleId unique identifier
     * @param licensePlate vehicle license plate
     * @param vehicleType the IVehicleType definition
     * @param ownerName owner's name
     * @param ownerContact owner's contact number
     */
    public Vehicle(String vehicleId, String licensePlate, IVehicleType vehicleType,
                   String ownerName, String ownerContact) {
        this.vehicleId = vehicleId;
        this.licensePlate = licensePlate;
        this.vehicleType = vehicleType;
        this.ownerName = ownerName;
        this.ownerContact = ownerContact;
        this.currentSpots = new ArrayList<>();
        this.entryTime = 0;
        this.isParked = false;
    }

    @Override
    public String getVehicleId() {
        return vehicleId;
    }

    @Override
    public String getLicensePlate() {
        return licensePlate;
    }

    @Override
    public IVehicleType getVehicleType() {
        return vehicleType;
    }

    @Override
    public String getOwnerName() {
        return ownerName;
    }

    @Override
    public String getOwnerContact() {
        return ownerContact;
    }

    @Override
    public IParkingSpot[] getCurrentSpots() {
        return currentSpots.toArray(new IParkingSpot[0]);
    }

    @Override
    public long getEntryTime() {
        return entryTime;
    }

    @Override
    public void setEntryTime(long entryTime) {
        this.entryTime = entryTime;
    }

    @Override
    public boolean parkAtSpot(IParkingSpot spot) {
        if (spot == null || !spot.canAccommodate(this.vehicleType)) {
            return false;
        }

        // Vehicles requiring single spot
        if (this.vehicleType.getSpotsRequired() != 1) {
            return false; // Use parkAtConsecutiveSpots for multi-spot vehicles
        }

        if (!spot.occupy(this.vehicleId)) {
            return false;
        }

        currentSpots.clear();
        currentSpots.add(spot);
        this.isParked = true;
        return true;
    }

    @Override
    public boolean parkAtConsecutiveSpots(IParkingSpot[] spots) {
        if (spots == null || spots.length == 0) {
            return false;
        }

        // Validate all spots can accommodate this vehicle
        for (IParkingSpot spot : spots) {
            if (spot == null || !spot.canAccommodate(this.vehicleType)) {
                return false;
            }
        }

        // Validate consecutive spots are on same floor
        int floor = spots[0].getFloorNumber();
        for (IParkingSpot spot : spots) {
            if (spot.getFloorNumber() != floor) {
                return false;
            }
        }

        // Occupy all spots
        for (IParkingSpot spot : spots) {
            if (!spot.occupy(this.vehicleId)) {
                // Rollback: vacate already occupied spots
                for (IParkingSpot occupiedSpot : spots) {
                    if (occupiedSpot.getCurrentVehicleId() != null &&
                        occupiedSpot.getCurrentVehicleId().equals(this.vehicleId)) {
                        occupiedSpot.vacate();
                    }
                }
                return false;
            }
        }

        currentSpots.clear();
        for (IParkingSpot spot : spots) {
            currentSpots.add(spot);
        }
        this.isParked = true;
        return true;
    }

    @Override
    public boolean unparkFromSpots() {
        if (!isParked || currentSpots.isEmpty()) {
            return false;
        }

        for (IParkingSpot spot : currentSpots) {
            String vacatedVehicleId = spot.vacate();
            if (vacatedVehicleId == null || !vacatedVehicleId.equals(this.vehicleId)) {
                return false;
            }
        }

        currentSpots.clear();
        this.isParked = false;
        return true;
    }

    @Override
    public boolean isParked() {
        return isParked && !currentSpots.isEmpty();
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "vehicleId='" + vehicleId + '\'' +
                ", licensePlate='" + licensePlate + '\'' +
                ", vehicleType=" + vehicleType.getDisplayName() +
                ", ownerName='" + ownerName + '\'' +
                ", isParked=" + isParked +
                '}';
    }
}
