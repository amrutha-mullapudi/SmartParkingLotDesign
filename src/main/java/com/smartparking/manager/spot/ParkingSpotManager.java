package com.smartparking.manager.spot;

import com.smartparking.core.entity.IParkingSpot;
import com.smartparking.core.entity.IVehicleType;
import com.smartparking.core.entity.IFloor;
import com.smartparking.strategy.allocator.ISpotAllocator;
import com.smartparking.strategy.allocator.SizeOptimizedAllocator;
import com.smartparking.exception.TransactionException;
import com.smartparking.exception.ParkingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of IParkingSpotManager.
 * Manages parking spot allocation across multiple floors using a configurable allocation strategy.
 */
public class ParkingSpotManager implements IParkingSpotManager {

    private final Map<Integer, IFloor> floors;
    private ISpotAllocator allocationStrategy;

    /**
     * Constructor for ParkingSpotManager.
     * Initializes with default SizeOptimizedAllocator strategy.
     */
    public ParkingSpotManager() {
        this.floors = new HashMap<>();
        this.allocationStrategy = new SizeOptimizedAllocator();
    }

    /**
     * Constructor for ParkingSpotManager with custom allocation strategy.
     * @param allocationStrategy the ISpotAllocator strategy to use
     */
    public ParkingSpotManager(ISpotAllocator allocationStrategy) {
        this.floors = new HashMap<>();
        this.allocationStrategy = allocationStrategy != null ? allocationStrategy : new SizeOptimizedAllocator();
    }

    /**
     * Set the allocation strategy at runtime.
     * @param allocationStrategy the new ISpotAllocator to use
     */
    public void setAllocationStrategy(ISpotAllocator allocationStrategy) {
        if (allocationStrategy != null) {
            this.allocationStrategy = allocationStrategy;
        }
    }

    @Override
    public IParkingSpot findAvailableSpot(IVehicleType vehicleType) {
        if (vehicleType == null) {
            return null;
        }

        // For single-spot vehicles
        if (vehicleType.getSpotsRequired() == 1) {
            // Query eligible floors for candidate spots
            for (int floorNum : vehicleType.getFloorEligibility()) {
                IFloor floor = floors.get(floorNum);
                if (floor == null || floor.isUnderMaintenance()) {
                    continue;
                }

                // Check if floor can accommodate vehicle weight
                if (!floor.canAccommodateAdditionalVehicle(vehicleType)) {
                    continue;
                }

                // Get available spots from floor
                IParkingSpot[] availableSpots = floor.getAvailableSpotsByVehicleType(vehicleType);
                if (availableSpots.length > 0) {
                    // Use allocation strategy to select best spot
                    return allocationStrategy.allocateSpot(availableSpots);
                }
            }
        }

        return null;
    }

    @Override
    public IParkingSpot[] findConsecutiveSpots(IVehicleType vehicleType, int count) {
        if (vehicleType == null || count <= 0) {
            return new IParkingSpot[0];
        }

        // For multi-spot vehicles
        for (int floorNum : vehicleType.getFloorEligibility()) {
            IFloor floor = floors.get(floorNum);
            if (floor == null || floor.isUnderMaintenance()) {
                continue;
            }

            // Check if floor can accommodate vehicle weight
            if (!floor.canAccommodateAdditionalVehicle(vehicleType)) {
                continue;
            }

            // Get consecutive spots from floor
            IParkingSpot[] consecutiveSpots = floor.getConsecutiveSpots(vehicleType, count);
            if (consecutiveSpots.length >= count) {
                // Use allocation strategy to select consecutive spots
                return allocationStrategy.allocateConsecutiveSpots(consecutiveSpots);
            }
        }

        return new IParkingSpot[0];
    }

    @Override
    public boolean allocateSpot(String spotId, String vehicleId) 
            throws TransactionException {
        if (spotId == null || spotId.isEmpty()) {
            throw new TransactionException("Spot ID cannot be null or empty");
        }
        
        if (vehicleId == null || vehicleId.isEmpty()) {
            throw new TransactionException("Vehicle ID cannot be null or empty");
        }

        // Find the spot and occupy it
        for (IFloor floor : floors.values()) {
            for (IParkingSpot spot : floor.getAllSpots()) {
                if (spot.getSpotId().equals(spotId)) {
                    if (!spot.occupy(vehicleId)) {
                        throw new TransactionException(spotId, "Failed to occupy spot");
                    }
                    return true;
                }
            }
        }

        throw new TransactionException(spotId, "Spot not found in any floor");
    }

    @Override
    public String releaseSpot(String spotId) 
            throws TransactionException {
        if (spotId == null || spotId.isEmpty()) {
            throw new TransactionException("Spot ID cannot be null or empty");
        }

        // Find the spot and vacate it
        for (IFloor floor : floors.values()) {
            for (IParkingSpot spot : floor.getAllSpots()) {
                if (spot.getSpotId().equals(spotId)) {
                    String vacatedVehicleId = spot.vacate();
                    if (vacatedVehicleId == null) {
                        throw new TransactionException(spotId, "Spot was already empty");
                    }
                    return vacatedVehicleId;
                }
            }
        }

        throw new TransactionException(spotId, "Spot not found in any floor");
    }

    @Override
    public int getTotalSpots() {
        int total = 0;
        for (IFloor floor : floors.values()) {
            total += floor.getTotalSpots();
        }
        return total;
    }

    @Override
    public int getAvailableSpotsCount() {
        int available = 0;
        for (IFloor floor : floors.values()) {
            available += floor.getAvailableSpotsCount();
        }
        return available;
    }

    @Override
    public double getOccupancyRate() {
        int total = getTotalSpots();
        if (total == 0) {
            return 0.0;
        }

        int occupied = total - getAvailableSpotsCount();
        return (double) occupied / total * 100;
    }

    @Override
    public boolean isAtFullCapacity() {
        return getAvailableSpotsCount() == 0;
    }

    @Override
    public boolean registerFloor(IFloor floor) 
            throws ParkingException {
        if (floor == null) {
            throw new ParkingException("Floor cannot be null");
        }

        int floorNum = floor.getFloorNumber();
        if (floors.containsKey(floorNum)) {
            throw new ParkingException("Floor already registered with number: " + floorNum);
        }

        floors.put(floorNum, floor);
        return true;
    }

    @Override
    public IFloor getFloor(int floorNumber) {
        return floors.get(floorNumber);
    }

    @Override
    public int getFloorCount() {
        return floors.size();
    }

    @Override
    public String toString() {
        return "ParkingSpotManager{" +
                "floors=" + floors.size() +
                ", totalSpots=" + getTotalSpots() +
                ", availableSpots=" + getAvailableSpotsCount() +
                ", strategy=" + allocationStrategy.getStrategyName() +
                '}';
    }
}
