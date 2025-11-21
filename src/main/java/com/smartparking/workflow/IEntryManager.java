package com.smartparking.workflow;

import com.smartparking.core.entity.IVehicle;
import com.smartparking.exception.ParkingException;

/**
 * Interface for vehicle entry workflow.
 * Handles the process of a vehicle entering the parking lot.
 */
public interface IEntryManager {

    /**
     * Process vehicle entry into the parking lot.
     * Validates vehicle, finds parking spot, creates transaction.
     * @param vehicle the IVehicle entering
     * @return transaction ID for the parked vehicle, null if entry failed
     * @throws ParkingException if entry process fails
     */
    String processEntry(IVehicle vehicle) throws ParkingException;

    /**
     * Get entry status/statistics.
     * @return formatted string with entry information
     */
    String getEntryStats();
}
