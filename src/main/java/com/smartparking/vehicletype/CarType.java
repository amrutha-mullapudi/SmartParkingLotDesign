package com.smartparking.vehicletype;

import com.smartparking.constant.VehicleSize;
import com.smartparking.constant.VehicleWeight;
import com.smartparking.constant.SpotSize;
import com.smartparking.core.impl.VehicleType;

/**
 * Concrete implementation of VehicleType for Cars and Sedans.
 * Characteristics:
 * - Size: MEDIUM (2-4.5 meters)
 * - Weight: MEDIUM (500-1500 kg)
 * - Spots Required: 1
 * - Floor Eligibility: Lower floors [0-3]
 */
public class CarType extends VehicleType {

    private static final String TYPE_ID = "CAR";
    private static final String DISPLAY_NAME = "Car";
    private static final VehicleSize SIZE = VehicleSize.MEDIUM;
    private static final VehicleWeight WEIGHT_CATEGORY = VehicleWeight.MEDIUM;
    private static final int WEIGHT_KG = 1200; // Average car weight
    private static final int SPOTS_REQUIRED = 1;
    private static final SpotSize SPOT_SIZE = SpotSize.MEDIUM;
    private static final int[] FLOOR_ELIGIBILITY = {0, 1, 2, 3}; // Lower floors

    /**
     * Constructor for CarType.
     */
    public CarType() {
        super(TYPE_ID, DISPLAY_NAME, SIZE, WEIGHT_CATEGORY, WEIGHT_KG, 
              SPOTS_REQUIRED, SPOT_SIZE, FLOOR_ELIGIBILITY);
    }
}
