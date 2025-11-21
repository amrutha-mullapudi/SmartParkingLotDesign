package com.smartparking.core.impl;

import com.smartparking.constant.VehicleSize;
import com.smartparking.constant.VehicleWeight;
import com.smartparking.constant.SpotSize;
import com.smartparking.core.entity.IVehicleType;

/**
 * Abstract base implementation of IVehicleType.
 * Provides common implementation for vehicle type definitions.
 * Concrete vehicle types (BikeType, CarType, BusType, TruckType) extend this class.
 */
public abstract class VehicleType implements IVehicleType {

    protected final String vehicleTypeId;
    protected final String displayName;
    protected final VehicleSize size;
    protected final VehicleWeight weightCategory;
    protected final int weightInKg;
    protected final int spotsRequired;
    protected final SpotSize spotSizeRequirement;
    protected final int[] floorEligibility;

    /**
     * Constructor for VehicleType.
     * @param vehicleTypeId unique ID for this vehicle type
     * @param displayName human-readable name
     * @param size vehicle size category
     * @param weightCategory weight category
     * @param weightInKg actual weight in kg
     * @param spotsRequired number of parking spots needed
     * @param spotSizeRequirement spot size requirement
     * @param floorEligibility array of floor numbers where this type can park
     */
    protected VehicleType(String vehicleTypeId, String displayName, VehicleSize size,
                         VehicleWeight weightCategory, int weightInKg, int spotsRequired,
                         SpotSize spotSizeRequirement, int[] floorEligibility) {
        this.vehicleTypeId = vehicleTypeId;
        this.displayName = displayName;
        this.size = size;
        this.weightCategory = weightCategory;
        this.weightInKg = weightInKg;
        this.spotsRequired = spotsRequired;
        this.spotSizeRequirement = spotSizeRequirement;
        this.floorEligibility = floorEligibility;
    }

    @Override
    public String getVehicleTypeId() {
        return vehicleTypeId;
    }

    @Override
    public VehicleSize getSize() {
        return size;
    }

    @Override
    public VehicleWeight getWeightCategory() {
        return weightCategory;
    }

    @Override
    public int getWeightInKg() {
        return weightInKg;
    }

    @Override
    public int getSpotsRequired() {
        return spotsRequired;
    }

    @Override
    public SpotSize getSpotSizeRequirement() {
        return spotSizeRequirement;
    }

    @Override
    public int[] getFloorEligibility() {
        return floorEligibility;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public boolean canFitInSpot(SpotSize spotSize) {
        return spotSize.canAccommodateVehicleSize(this.size);
    }

    @Override
    public boolean canParkOnFloor(int floorNumber) {
        for (int eligibleFloor : floorEligibility) {
            if (eligibleFloor == floorNumber) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "VehicleType{" +
                "vehicleTypeId='" + vehicleTypeId + '\'' +
                ", displayName='" + displayName + '\'' +
                ", size=" + size +
                ", weightCategory=" + weightCategory +
                ", weightInKg=" + weightInKg +
                '}';
    }
}
