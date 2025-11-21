package com.smartparking.vehicletype;

import com.smartparking.constant.VehicleSize;
import com.smartparking.constant.VehicleWeight;
import com.smartparking.constant.SpotSize;
import com.smartparking.core.impl.VehicleType;

/**
 * Concrete implementation of VehicleType for Bikes and Scooters.
 * Characteristics:
 * - Size: SMALL (< 2 meters)
 * - Weight: LIGHT (< 500 kg)
 * - Spots Required: 1
 * - Floor Eligibility: All floors [0-5]
 */
public class BikeType extends VehicleType {

    private static final String TYPE_ID = "BIKE";
    private static final String DISPLAY_NAME = "Bike";
    private static final VehicleSize SIZE = VehicleSize.SMALL;
    private static final VehicleWeight WEIGHT_CATEGORY = VehicleWeight.LIGHT;
    private static final int WEIGHT_KG = 150; // Average bike weight
    private static final int SPOTS_REQUIRED = 1;
    private static final SpotSize SPOT_SIZE = SpotSize.SMALL;
    private static final int[] FLOOR_ELIGIBILITY = {0, 1, 2, 3, 4, 5}; // All floors

    /**
     * Constructor for BikeType.
     */
    public BikeType() {
        super(TYPE_ID, DISPLAY_NAME, SIZE, WEIGHT_CATEGORY, WEIGHT_KG, 
              SPOTS_REQUIRED, SPOT_SIZE, FLOOR_ELIGIBILITY);
    }
}
