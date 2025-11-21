package com.smartparking.vehicletype;

import com.smartparking.constant.VehicleSize;
import com.smartparking.constant.VehicleWeight;
import com.smartparking.constant.SpotSize;
import com.smartparking.core.impl.VehicleType;

/**
 * Concrete implementation of VehicleType for Trucks.
 * Characteristics:
 * - Size: EXTRA_LARGE (> 6 meters)
 * - Weight: HEAVY (> 1500 kg)
 * - Spots Required: 3-4 consecutive spots
 * - Floor Eligibility: Ground floor only [0]
 * Note: We default to 3 spots for standard commercial trucks.
 */
public class TruckType extends VehicleType {

    private static final String TYPE_ID = "TRUCK";
    private static final String DISPLAY_NAME = "Truck";
    private static final VehicleSize SIZE = VehicleSize.EXTRA_LARGE;
    private static final VehicleWeight WEIGHT_CATEGORY = VehicleWeight.HEAVY;
    private static final int WEIGHT_KG = 8000; // Average truck weight
    private static final int SPOTS_REQUIRED = 3; // Typically 3-4 for standard trucks
    private static final SpotSize SPOT_SIZE = SpotSize.EXTRA_LARGE;
    private static final int[] FLOOR_ELIGIBILITY = {0}; // Ground floor only

    /**
     * Constructor for TruckType.
     */
    public TruckType() {
        super(TYPE_ID, DISPLAY_NAME, SIZE, WEIGHT_CATEGORY, WEIGHT_KG, 
              SPOTS_REQUIRED, SPOT_SIZE, FLOOR_ELIGIBILITY);
    }
}
