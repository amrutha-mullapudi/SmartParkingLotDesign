package com.smartparking.core.impl;

import com.smartparking.core.entity.IFloor;
import com.smartparking.core.entity.IParkingSpot;
import com.smartparking.core.entity.IVehicleType;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of IFloor representing a parking floor in the parking lot.
 * A floor manages multiple parking spots and tracks floor-level constraints
 * such as weight capacity and current load.
 */
public class Floor implements IFloor {

    private final int floorNumber;
    private final IParkingSpot[] parkingSpots;
    private final int maxFloorWeightCapacity;
    private int currentFloorLoad;
    private boolean underMaintenance;

    /**
     * Constructor for Floor.
     * @param floorNumber floor number (0-indexed)
     * @param parkingSpots array of parking spots on this floor
     * @param maxFloorWeightCapacity maximum weight capacity for this floor
     */
    public Floor(int floorNumber, IParkingSpot[] parkingSpots, int maxFloorWeightCapacity) {
        this.floorNumber = floorNumber;
        this.parkingSpots = parkingSpots;
        this.maxFloorWeightCapacity = maxFloorWeightCapacity;
        this.currentFloorLoad = 0;
        this.underMaintenance = false;
    }

    @Override
    public int getFloorNumber() {
        return floorNumber;
    }

    @Override
    public int getTotalSpots() {
        return parkingSpots.length;
    }

    @Override
    public int getAvailableSpotsCount() {
        int count = 0;
        for (IParkingSpot spot : parkingSpots) {
            if (spot.isAvailable()) {
                count++;
            }
        }
        return count;
    }

    @Override
    public int getCurrentFloorLoad() {
        return currentFloorLoad;
    }

    @Override
    public int getMaxFloorWeightCapacity() {
        return maxFloorWeightCapacity;
    }

    @Override
    public boolean canAccommodateAdditionalVehicle(IVehicleType vehicleType) {
        if (vehicleType == null || underMaintenance) {
            return false;
        }

        // Check if adding this vehicle would exceed weight capacity
        return (currentFloorLoad + vehicleType.getWeightInKg()) <= maxFloorWeightCapacity;
    }

    @Override
    public IParkingSpot[] getAvailableSpotsByVehicleType(IVehicleType vehicleType) {
        if (vehicleType == null || underMaintenance) {
            return new IParkingSpot[0];
        }

        List<IParkingSpot> availableSpots = new ArrayList<>();
        for (IParkingSpot spot : parkingSpots) {
            if (spot.canAccommodate(vehicleType)) {
                availableSpots.add(spot);
            }
        }

        return availableSpots.toArray(new IParkingSpot[0]);
    }

    @Override
    public IParkingSpot[] getConsecutiveSpots(IVehicleType vehicleType, int consecutiveCount) {
        if (vehicleType == null || consecutiveCount <= 0 || underMaintenance) {
            return new IParkingSpot[0];
        }

        if (consecutiveCount > parkingSpots.length) {
            return new IParkingSpot[0];
        }

        // Iterate through spots to find consecutive available spots
        for (int i = 0; i <= parkingSpots.length - consecutiveCount; i++) {
            List<IParkingSpot> consecutiveList = new ArrayList<>();
            boolean allConsecutiveValid = true;

            // Check if next 'consecutiveCount' spots are available and compatible
            for (int j = i; j < i + consecutiveCount; j++) {
                IParkingSpot spot = parkingSpots[j];
                if (!spot.canAccommodate(vehicleType)) {
                    allConsecutiveValid = false;
                    break;
                }
                consecutiveList.add(spot);
            }

            if (allConsecutiveValid) {
                return consecutiveList.toArray(new IParkingSpot[0]);
            }
        }

        return new IParkingSpot[0];
    }

    @Override
    public boolean registerParkedVehicle(IVehicleType vehicleType) {
        if (vehicleType == null) {
            return false;
        }

        currentFloorLoad += vehicleType.getWeightInKg();
        return true;
    }

    @Override
    public boolean unregisterParkedVehicle(IVehicleType vehicleType) {
        if (vehicleType == null) {
            return false;
        }

        currentFloorLoad -= vehicleType.getWeightInKg();
        if (currentFloorLoad < 0) {
            currentFloorLoad = 0; // Prevent negative load
        }
        return true;
    }

    @Override
    public IParkingSpot[] getAllSpots() {
        return parkingSpots;
    }

    @Override
    public boolean isUnderMaintenance() {
        return underMaintenance;
    }

    @Override
    public void setUnderMaintenance(boolean underMaintenance) {
        this.underMaintenance = underMaintenance;
    }

    @Override
    public String toString() {
        return "Floor{" +
                "floorNumber=" + floorNumber +
                ", totalSpots=" + parkingSpots.length +
                ", availableSpots=" + getAvailableSpotsCount() +
                ", currentLoad=" + currentFloorLoad +
                ", maxCapacity=" + maxFloorWeightCapacity +
                '}';
    }
}
