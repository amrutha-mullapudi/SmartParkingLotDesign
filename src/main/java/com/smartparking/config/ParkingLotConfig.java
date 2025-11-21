package com.smartparking.config;

import com.smartparking.controller.IParkingLotManager;
import com.smartparking.controller.ParkingLotManager;
import com.smartparking.core.impl.Floor;
import com.smartparking.core.impl.ParkingSpot;
import com.smartparking.constant.SpotSize;
import com.smartparking.strategy.allocator.ISpotAllocator;
import com.smartparking.strategy.allocator.LoadBalancedAllocator;
import com.smartparking.strategy.allocator.NearestToExitAllocator;
import com.smartparking.strategy.allocator.SizeOptimizedAllocator;
import com.smartparking.strategy.feecalculator.IFeeCalculator;
import com.smartparking.strategy.feecalculator.ProgressiveFeeCalculator;
import com.smartparking.strategy.feecalculator.StandardFeeCalculator;
import com.smartparking.strategy.feecalculator.TimeBasedFeeCalculator;
import com.smartparking.vehicletype.BikeType;
import com.smartparking.vehicletype.BusType;
import com.smartparking.vehicletype.CarType;
import com.smartparking.vehicletype.TruckType;
import com.smartparking.exception.ParkingException;

/**
 * Configuration class for initializing the Smart Parking Lot system.
 * 
 * This class serves as a factory and configuration hub that:
 * 1. Creates and registers vehicle types
 * 2. Selects allocation and fee calculation strategies
 * 3. Initializes the parking lot structure (floors and spots)
 * 4. Returns a fully configured ParkingLotManager instance
 */
public class ParkingLotConfig {

    // ===== STRATEGY SELECTION =====
    // Change these constants to switch between different strategies at runtime
    
    /**
     * Available spot allocation strategies:
     * - "LOAD_BALANCED": Distributes vehicles evenly across parking lot
     * - "SIZE_OPTIMIZED": Assigns smallest adequate spot to each vehicle
     * - "NEAREST_TO_EXIT": Prioritizes spots closest to exit for faster departure
     */
    private static final String SPOT_ALLOCATOR_STRATEGY = "LOAD_BALANCED";

    /**
     * Available fee calculation strategies:
     * - "STANDARD": Flat hourly rate based on vehicle size
     * - "PROGRESSIVE": Rate increases with parking duration
     * - "TIME_BASED": Peak/off-peak pricing
     */
    private static final String FEE_CALCULATOR_STRATEGY = "STANDARD";

    // ===== PARKING LOT STRUCTURE =====
    private static final int NUM_FLOORS = 3;
    private static final int SPOTS_PER_FLOOR = 20;
    
    // Spot distribution per floor (total must equal SPOTS_PER_FLOOR)
    private static final int SMALL_SPOTS = 10;   // For bikes
    private static final int MEDIUM_SPOTS = 6;   // For cars
    private static final int LARGE_SPOTS = 3;    // For SUVs, vans
    private static final int EXTRA_LARGE_SPOTS = 1; // For buses, trucks

    /**
     * Creates and returns a fully configured ParkingLotManager instance.
     * This is the main entry point for initializing the system.
     * 
     * @return configured IParkingLotManager ready for use
     * @throws ParkingException if configuration fails
     */
    public static IParkingLotManager createConfiguredParkingLot() throws ParkingException {
        System.out.println("=== Initializing Smart Parking Lot System ===\n");

        // Step 1: Create the parking lot manager
        ParkingLotManager parkingLot = new ParkingLotManager();
        
        // Step 2: Register vehicle types
        System.out.println("Step 1: Registering vehicle types...");
        registerVehicleTypes(parkingLot);
        
        // Step 3: Select and configure strategies
        System.out.println("Step 2: Configuring strategies...");
        ISpotAllocator allocator = selectSpotAllocator();
        IFeeCalculator feeCalculator = selectFeeCalculator();
        System.out.println("  ✓ Spot Allocator: " + allocator.getStrategyName());
        System.out.println("  ✓ Fee Calculator: " + feeCalculator.getStrategyName() + "\n");
        
        // Step 4: Initialize parking lot structure
        System.out.println("Step 3: Initializing parking lot structure...");
        initializeParkingLotStructure(parkingLot, allocator, feeCalculator);
        
        System.out.println("\n=== Parking Lot System Ready ===\n");
        return parkingLot;
    }

    /**
     * Registers all supported vehicle types into the parking lot.
     * Each vehicle type defines properties, size, weight, and floor eligibility.
     */
    private static void registerVehicleTypes(ParkingLotManager parkingLot) throws ParkingException {
        parkingLot.registerVehicleType(new BikeType());
        parkingLot.registerVehicleType(new CarType());
        parkingLot.registerVehicleType(new BusType());
        parkingLot.registerVehicleType(new TruckType());
        
        System.out.println("  ✓ 4 vehicle types registered (Bike, Car, Bus, Truck)");
    }

    /**
     * Selects the spot allocation strategy based on configuration.
     * This demonstrates the Strategy pattern - easy to switch algorithms.
     * 
     * @return selected ISpotAllocator implementation
     */
    private static ISpotAllocator selectSpotAllocator() {
        switch (SPOT_ALLOCATOR_STRATEGY) {
            case "SIZE_OPTIMIZED":
                return new SizeOptimizedAllocator();
            case "NEAREST_TO_EXIT":
                return new NearestToExitAllocator();
            case "LOAD_BALANCED":
            default:
                return new LoadBalancedAllocator();
        }
    }

    /**
     * Selects the fee calculation strategy based on configuration.
     * Easy to switch between different pricing models.
     * 
     * @return selected IFeeCalculator implementation
     */
    private static IFeeCalculator selectFeeCalculator() {
        switch (FEE_CALCULATOR_STRATEGY) {
            case "PROGRESSIVE":
                return new ProgressiveFeeCalculator();
            case "TIME_BASED":
                return new TimeBasedFeeCalculator();
            case "STANDARD":
            default:
                return new StandardFeeCalculator();
        }
    }

    /**
     * Initializes the parking lot with floors and parking spots.
     * Creates a realistic parking lot structure with varied spot sizes.
     */
    private static void initializeParkingLotStructure(
            ParkingLotManager parkingLot,
            ISpotAllocator allocator,
            IFeeCalculator feeCalculator) throws ParkingException {
        
        for (int floorNum = 0; floorNum < NUM_FLOORS; floorNum++) {
            String floorId = "F" + floorNum;
            Floor floor = new Floor(floorId, SPOTS_PER_FLOOR);
            
            // Add spots to floor with variety of sizes
            int spotIndex = 0;
            
            // Add SMALL spots (for bikes)
            for (int i = 0; i < SMALL_SPOTS; i++) {
                String spotId = floorId + "-S" + (spotIndex++);
                floor.addParkingSpot(new ParkingSpot(spotId, SpotSize.SMALL, floor));
            }
            
            // Add MEDIUM spots (for cars)
            for (int i = 0; i < MEDIUM_SPOTS; i++) {
                String spotId = floorId + "-S" + (spotIndex++);
                floor.addParkingSpot(new ParkingSpot(spotId, SpotSize.MEDIUM, floor));
            }
            
            // Add LARGE spots (for SUVs, vans)
            for (int i = 0; i < LARGE_SPOTS; i++) {
                String spotId = floorId + "-S" + (spotIndex++);
                floor.addParkingSpot(new ParkingSpot(spotId, SpotSize.LARGE, floor));
            }
            
            // Add EXTRA_LARGE spots (for buses, trucks)
            for (int i = 0; i < EXTRA_LARGE_SPOTS; i++) {
                String spotId = floorId + "-S" + (spotIndex++);
                floor.addParkingSpot(new ParkingSpot(spotId, SpotSize.EXTRA_LARGE, floor));
            }
            
            // Register floor with parking lot
            parkingLot.registerFloor(floor, allocator, feeCalculator);
            
            System.out.println("  ✓ Floor " + floorId + ": " + SPOTS_PER_FLOOR + " spots " +
                    "(" + SMALL_SPOTS + " small, " + MEDIUM_SPOTS + " medium, " +
                    LARGE_SPOTS + " large, " + EXTRA_LARGE_SPOTS + " extra-large)");
        }
    }

    /**
     * Gets current configuration as a readable string.
     * Useful for logging and debugging.
     * 
     * @return configuration summary
     */
    public static String getConfigurationSummary() {
        return String.format(
            "Parking Lot Configuration:\n" +
            "  Structure: %d floors × %d spots/floor = %d total spots\n" +
            "  Spot Distribution: %d SMALL, %d MEDIUM, %d LARGE, %d EXTRA_LARGE per floor\n" +
            "  Allocation Strategy: %s\n" +
            "  Fee Calculation Strategy: %s",
            NUM_FLOORS, SPOTS_PER_FLOOR, NUM_FLOORS * SPOTS_PER_FLOOR,
            SMALL_SPOTS, MEDIUM_SPOTS, LARGE_SPOTS, EXTRA_LARGE_SPOTS,
            SPOT_ALLOCATOR_STRATEGY, FEE_CALCULATOR_STRATEGY
        );
    }
}
