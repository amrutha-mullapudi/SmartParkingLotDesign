package com.smartparking.workflow;

import com.smartparking.core.entity.IVehicle;
import com.smartparking.core.entity.IParkingSpot;
import com.smartparking.core.entity.ITransaction;
import com.smartparking.manager.spot.IParkingSpotManager;
import com.smartparking.manager.transaction.ITransactionManager;
import com.smartparking.exception.ParkingException;
import com.smartparking.exception.NoSpotAvailableException;
import com.smartparking.exception.InvalidVehicleException;
import java.util.UUID;

/**
 * Implementation of IEntryManager.
 * Orchestrates the vehicle entry workflow.
 */
public class EntryManager implements IEntryManager {

    private final IParkingSpotManager spotManager;
    private final ITransactionManager transactionManager;
    private int totalEntriesProcessed;

    /**
     * Constructor for EntryManager.
     * @param spotManager the IParkingSpotManager to use
     * @param transactionManager the ITransactionManager to use
     */
    public EntryManager(IParkingSpotManager spotManager, ITransactionManager transactionManager) {
        this.spotManager = spotManager;
        this.transactionManager = transactionManager;
        this.totalEntriesProcessed = 0;
    }

    @Override
    public String processEntry(IVehicle vehicle) throws ParkingException {
        if (vehicle == null) {
            throw new InvalidVehicleException("Vehicle is null");
        }

        // Validate vehicle
        if (vehicle.getLicensePlate() == null || vehicle.getLicensePlate().isEmpty()) {
            throw new InvalidVehicleException("License plate is missing");
        }

        if (vehicle.getVehicleType() == null) {
            throw new InvalidVehicleException("Vehicle type is not defined");
        }

        // Find available spot(s)
        IParkingSpot[] spotsToOccupy;
        if (vehicle.getVehicleType().getSpotsRequired() == 1) {
            IParkingSpot spot = spotManager.findAvailableSpot(vehicle.getVehicleType());
            if (spot == null) {
                throw new NoSpotAvailableException(vehicle.getVehicleType().getDisplayName());
            }
            spotsToOccupy = new IParkingSpot[]{spot};
        } else {
            spotsToOccupy = spotManager.findConsecutiveSpots(
                vehicle.getVehicleType(),
                vehicle.getVehicleType().getSpotsRequired()
            );
            if (spotsToOccupy == null || spotsToOccupy.length == 0) {
                throw new NoSpotAvailableException(vehicle.getVehicleType().getDisplayName());
            }
        }

        // Park vehicle at spot(s)
        boolean parkingSuccessful;
        if (spotsToOccupy.length == 1) {
            parkingSuccessful = vehicle.parkAtSpot(spotsToOccupy[0]);
        } else {
            parkingSuccessful = vehicle.parkAtConsecutiveSpots(spotsToOccupy);
        }

        if (!parkingSuccessful) {
            throw new ParkingException("Failed to park vehicle at spot");
        }

        // Set entry time
        long entryTime = System.currentTimeMillis();
        vehicle.setEntryTime(entryTime);

        // Create transaction
        String[] spotIds = new String[spotsToOccupy.length];
        for (int i = 0; i < spotsToOccupy.length; i++) {
            spotIds[i] = spotsToOccupy[i].getSpotId();
        }

        ITransaction transaction = transactionManager.createTransaction(
            vehicle.getVehicleId(),
            spotIds,
            entryTime
        );

        if (transaction == null) {
            throw new ParkingException("Failed to create transaction");
        }

        totalEntriesProcessed++;
        return transaction.getTransactionId();
    }

    @Override
    public String getEntryStats() {
        return "EntryManager{" +
                "totalEntriesProcessed=" + totalEntriesProcessed +
                ", activeParked=" + spotManager.getTotalSpots() - spotManager.getAvailableSpotsCount() +
                ", occupancyRate=" + String.format("%.2f%%", spotManager.getOccupancyRate()) +
                '}';
    }

    /**
     * Get total entries processed.
     */
    public int getTotalEntriesProcessed() {
        return totalEntriesProcessed;
    }
}
