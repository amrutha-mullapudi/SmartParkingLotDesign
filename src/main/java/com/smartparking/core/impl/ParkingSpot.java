package com.smartparking.core.impl;

import com.smartparking.constant.SpotSize;
import com.smartparking.constant.SpotStatus;
import com.smartparking.core.entity.IParkingSpot;
import com.smartparking.core.entity.IVehicleType;

/**
 * Implementation of IParkingSpot representing a single parking space in the parking lot.
 * Each spot is atomic and manages its own availability and vehicle occupancy.
 */
public class ParkingSpot implements IParkingSpot {

    private final String spotId;
    private final int floorNumber;
    private final SpotSize spotSize;
    private final int maxWeightCapacity;
    private SpotStatus status;
    private String currentVehicleId;

    /**
     * Constructor for ParkingSpot.
     * @param spotId unique identifier (e.g., "F0-S1")
     * @param floorNumber floor number where spot is located
     * @param spotSize size of this parking spot
     * @param maxWeightCapacity maximum weight this spot can support in kg
     */
    public ParkingSpot(String spotId, int floorNumber, SpotSize spotSize, int maxWeightCapacity) {
        this.spotId = spotId;
        this.floorNumber = floorNumber;
        this.spotSize = spotSize;
        this.maxWeightCapacity = maxWeightCapacity;
        this.status = SpotStatus.AVAILABLE;
        this.currentVehicleId = null;
    }

    @Override
    public String getSpotId() {
        return spotId;
    }

    @Override
    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public SpotSize getSpotSize() {
        return spotSize;
    }

    @Override
    public int getMaxWeightCapacity() {
        return maxWeightCapacity;
    }

    @Override
    public SpotStatus getStatus() {
        return status;
    }

    @Override
    public boolean isAvailable() {
        return status == SpotStatus.AVAILABLE && currentVehicleId == null;
    }

    @Override
    public String getCurrentVehicleId() {
        return currentVehicleId;
    }

    @Override
    public boolean canAccommodate(IVehicleType vehicleType) {
        if (vehicleType == null) {
            return false;
        }

        // Check if spot size can fit this vehicle
        if (!spotSize.canAccommodateVehicleSize(vehicleType.getSize())) {
            return false;
        }

        // Check if spot weight capacity is sufficient
        if (vehicleType.getWeightInKg() > maxWeightCapacity) {
            return false;
        }

        // Spot must be available
        if (!isAvailable()) {
            return false;
        }

        return true;
    }

    @Override
    public boolean occupy(String vehicleId) {
        if (vehicleId == null || !isAvailable()) {
            return false;
        }

        this.currentVehicleId = vehicleId;
        this.status = SpotStatus.OCCUPIED;
        return true;
    }

    @Override
    public String vacate() {
        if (currentVehicleId == null) {
            return null;
        }

        String vacatedVehicleId = this.currentVehicleId;
        this.currentVehicleId = null;
        this.status = SpotStatus.AVAILABLE;
        return vacatedVehicleId;
    }

    @Override
    public void setStatus(SpotStatus status) {
        this.status = status;
        // If setting to MAINTENANCE or RESERVED, clear vehicle if it exists
        if ((status == SpotStatus.MAINTENANCE || status == SpotStatus.RESERVED) &&
            currentVehicleId != null) {
            currentVehicleId = null;
        }
    }

    @Override
    public String toString() {
        return "ParkingSpot{" +
                "spotId='" + spotId + '\'' +
                ", floorNumber=" + floorNumber +
                ", spotSize=" + spotSize +
                ", status=" + status +
                ", currentVehicleId='" + currentVehicleId + '\'' +
                '}';
    }
}
