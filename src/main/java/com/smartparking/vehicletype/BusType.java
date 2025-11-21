package com.smartparking.vehicletype;

import com.smartparking.constant.VehicleSize;
import com.smartparking.constant.VehicleWeight;
import com.smartparking.constant.SpotSize;
import com.smartparking.core.impl.VehicleType;

/**
 * Concrete implementation of VehicleType for Buses.
 * Characteristics:
 * - Size: LARGE (4.5-6 meters)
 * - Weight: HEAVY (> 1500 kg)
 * - Spots Required: 2-3 consecutive spots
 * - Floor Eligibility: Ground floor only [0]
 * Note: We default to 2 spots for standard city buses.
 */
public class BusType extends VehicleType {

    private static final String TYPE_ID = "BUS";
    private static final String DISPLAY_NAME = "Bus";
    private static final VehicleSize SIZE = VehicleSize.LARGE;
    private static final VehicleWeight WEIGHT_CATEGORY = VehicleWeight.HEAVY;
    private static final int WEIGHT_KG = 4000; // Average bus weight
    private static final int SPOTS_REQUIRED = 2; // Typically 2-3 for standard buses
    private static final SpotSize SPOT_SIZE = SpotSize.LARGE;
    private static final int[] FLOOR_ELIGIBILITY = {0}; // Ground floor only

    /**
     * Constructor for BusType.
     */
    public BusType() {
        super(TYPE_ID, DISPLAY_NAME, SIZE, WEIGHT_CATEGORY, WEIGHT_KG, 
              SPOTS_REQUIRED, SPOT_SIZE, FLOOR_ELIGIBILITY);
    }
}
