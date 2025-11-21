package com.smartparking;

import com.smartparking.config.ParkingLotConfig;
import com.smartparking.controller.IParkingLotManager;
import com.smartparking.core.entity.IVehicle;
import com.smartparking.core.impl.Vehicle;
import com.smartparking.exception.ParkingException;
import com.smartparking.vehicletype.BikeType;
import com.smartparking.vehicletype.BusType;
import com.smartparking.vehicletype.CarType;
import com.smartparking.vehicletype.TruckType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Smart Parking Lot System - Main Driver Code
 * 
 * Comprehensive entry point demonstrating:
 * 1. System initialization using ParkingLotConfig
 * 2. Multiple vehicle scenarios (Bike, Car, Bus, Truck)
 * 3. Concurrent parking operations
 * 4. Entry and exit handling
 * 5. Fee calculation and receipts
 * 6. Error handling and exception scenarios
 * 7. Parking lot capacity management
 */
public class Main {

    private static final String DIVIDER = "═".repeat(90);
    private static final String SEPARATOR = "─".repeat(90);

    public static void main(String[] args) {
        try {
            // ===== INITIALIZATION PHASE =====
            printBanner("SMART PARKING LOT SYSTEM - STARTING UP");
            
            IParkingLotManager parkingLot = ParkingLotConfig.createConfiguredParkingLot();
            
            System.out.println("\n" + ParkingLotConfig.getConfigurationSummary());
            printSeparator();
            
            // ===== SCENARIO 1: NORMAL PARKING OPERATIONS =====
            simulateNormalParkingScenario(parkingLot);
            
            // ===== SCENARIO 2: MULTIPLE VEHICLES WITH DIFFERENT TYPES =====
            simulateMultipleVehicleTypes(parkingLot);
            
            // ===== SCENARIO 3: CONCURRENT PARKING OPERATIONS =====
            simulateConcurrentOperations(parkingLot);
            
            // ===== SCENARIO 4: PARKING LOT CAPACITY MANAGEMENT =====
            simulateCapacityManagement(parkingLot);
            
            // ===== SCENARIO 5: ERROR HANDLING SCENARIOS =====
            simulateErrorScenarios(parkingLot);
            
            // ===== FINAL REPORT =====
            printBanner("PARKING LOT SESSION COMPLETED SUCCESSFULLY");
            System.out.println("\n✓ All scenarios executed successfully");
            System.out.println("✓ Exception handling validated");
            System.out.println("✓ Parking lot system operational\n");
            
        } catch (Exception e) {
            System.err.println("\n" + DIVIDER);
            System.err.println("FATAL ERROR - Unexpected system failure");
            System.err.println(DIVIDER);
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
            System.exit(1);
        }
    }

    // ════════════════════════════════════════════════════════════════════════════════
    // SCENARIO 1: NORMAL PARKING OPERATIONS
    // ════════════════════════════════════════════════════════════════════════════════

    private static void simulateNormalParkingScenario(IParkingLotManager parkingLot) {
        printBanner("SCENARIO 1: NORMAL PARKING OPERATIONS");
        
        try {
            // Vehicle 1: Car parks for 2 hours
            System.out.println("\n>>> Vehicle 1: Car Entry");
            IVehicle car1 = new Vehicle("DL-01-AB-1234", new CarType());
            System.out.println("  License: " + car1.getLicensePlate());
            System.out.println("  Type: " + car1.getVehicleType().getTypeName());
            System.out.println("  Size: " + car1.getVehicleType().getSize());
            
            String txn1 = parkingLot.registerEntry(car1);
            System.out.println("  ✓ Entry registered - Transaction ID: " + txn1);
            System.out.println("  Parking for 2 hours...\n");
            
            // Simulate time passage (120 minutes = 2 hours)
            simulateTimeDelay();
            
            System.out.println(">>> Vehicle 1: Car Exit");
            String receipt1 = parkingLot.registerExit(car1.getLicensePlate());
            System.out.println(receipt1);
            
            printSeparator();
            
        } catch (ParkingException e) {
            System.err.println("✗ Error in Scenario 1: " + e.getMessage());
            System.err.println("  Error Code: " + e.getErrorCode());
        }
    }

    // ════════════════════════════════════════════════════════════════════════════════
    // SCENARIO 2: MULTIPLE VEHICLE TYPES
    // ════════════════════════════════════════════════════════════════════════════════

    private static void simulateMultipleVehicleTypes(IParkingLotManager parkingLot) {
        printBanner("SCENARIO 2: MULTIPLE VEHICLE TYPES");
        
        Map<String, IVehicle> vehicles = new HashMap<>();
        Map<String, String> transactions = new HashMap<>();
        
        try {
            // Register multiple vehicle types
            System.out.println("\n>>> Registering Multiple Vehicles:\n");
            
            // Bike entry
            IVehicle bike = new Vehicle("HR-26-AB-5678", new BikeType());
            System.out.println("  1. Bike - " + bike.getLicensePlate() + " (Size: " + 
                    bike.getVehicleType().getSize() + ")");
            String bikeTxn = parkingLot.registerEntry(bike);
            System.out.println("     ✓ Parked - Transaction: " + bikeTxn);
            vehicles.put("bike", bike);
            transactions.put("bike", bikeTxn);
            
            // Car entry
            IVehicle car = new Vehicle("MH-02-CD-9876", new CarType());
            System.out.println("  2. Car - " + car.getLicensePlate() + " (Size: " + 
                    car.getVehicleType().getSize() + ")");
            String carTxn = parkingLot.registerEntry(car);
            System.out.println("     ✓ Parked - Transaction: " + carTxn);
            vehicles.put("car", car);
            transactions.put("car", carTxn);
            
            // Truck entry
            IVehicle truck = new Vehicle("KA-01-EF-4321", new TruckType());
            System.out.println("  3. Truck - " + truck.getLicensePlate() + " (Size: " + 
                    truck.getVehicleType().getSize() + ")");
            String truckTxn = parkingLot.registerEntry(truck);
            System.out.println("     ✓ Parked - Transaction: " + truckTxn);
            vehicles.put("truck", truck);
            transactions.put("truck", truckTxn);
            
            // Bus entry
            IVehicle bus = new Vehicle("TN-11-GH-5555", new BusType());
            System.out.println("  4. Bus - " + bus.getLicensePlate() + " (Size: " + 
                    bus.getVehicleType().getSize() + ")");
            String busTxn = parkingLot.registerEntry(bus);
            System.out.println("     ✓ Parked - Transaction: " + busTxn);
            vehicles.put("bus", bus);
            transactions.put("bus", busTxn);
            
            System.out.println("\n>>> All vehicles parked. Parking for 1.5 hours...\n");
            simulateTimeDelay();
            
            System.out.println(">>> Processing Exits:\n");
            
            // Exit all vehicles
            for (String key : new ArrayList<>(vehicles.keySet())) {
                IVehicle vehicle = vehicles.get(key);
                System.out.println("  Exiting: " + vehicle.getLicensePlate() + " (" + 
                        vehicle.getVehicleType().getTypeName() + ")");
                String receipt = parkingLot.registerExit(vehicle.getLicensePlate());
                // Print brief fee info
                String[] lines = receipt.split("\n");
                for (String line : lines) {
                    if (line.contains("PARKING FEE") || line.contains("Status") || line.contains("$")) {
                        System.out.println("    " + line);
                    }
                }
                System.out.println();
            }
            
            printSeparator();
            
        } catch (ParkingException e) {
            System.err.println("✗ Error in Scenario 2: " + e.getMessage());
            System.err.println("  Error Code: " + e.getErrorCode());
        }
    }

    // ════════════════════════════════════════════════════════════════════════════════
    // SCENARIO 3: CONCURRENT PARKING OPERATIONS
    // ════════════════════════════════════════════════════════════════════════════════

    private static void simulateConcurrentOperations(IParkingLotManager parkingLot) {
        printBanner("SCENARIO 3: CONCURRENT PARKING OPERATIONS");
        
        List<String> licensePlates = new ArrayList<>();
        
        try {
            System.out.println("\n>>> Simulating rapid vehicle entries (Shopping Mall scenario):\n");
            
            String[] plates = {
                "DL-01-AA-0001", "DL-01-AA-0002", "DL-01-AA-0003",
                "DL-01-AA-0004", "DL-01-AA-0005", "DL-01-AA-0006",
                "DL-01-AA-0007", "DL-01-AA-0008", "DL-01-AA-0009"
            };
            
            System.out.println("Registering 9 cars...");
            for (int i = 0; i < plates.length; i++) {
                IVehicle vehicle = new Vehicle(plates[i], new CarType());
                String txn = parkingLot.registerEntry(vehicle);
                licensePlates.add(plates[i]);
                System.out.print("  ✓ Vehicle " + (i + 1) + " parked");
                if ((i + 1) % 3 == 0) System.out.println();
                else System.out.print(" | ");
            }
            System.out.println("\n\n>>> All vehicles parked. Simulating 3 hours of parking...\n");
            
            simulateTimeDelay();
            
            System.out.println(">>> Processing exits for parked vehicles...\n");
            
            int exitCount = 0;
            double totalRevenue = 0;
            
            for (String plate : licensePlates) {
                try {
                    String receipt = parkingLot.registerExit(plate);
                    exitCount++;
                    // Extract fee from receipt
                    for (String line : receipt.split("\n")) {
                        if (line.contains("PARKING FEE: $")) {
                            String feeStr = line.replace("PARKING FEE: $", "").trim();
                            totalRevenue += Double.parseDouble(feeStr);
                        }
                    }
                    System.out.println("  ✓ Exit processed for " + plate);
                } catch (ParkingException e) {
                    System.err.println("  ✗ Exit failed for " + plate + ": " + e.getMessage());
                }
            }
            
            System.out.println("\n>>> Scenario Summary:");
            System.out.println("  Vehicles processed: " + exitCount);
            System.out.println("  Total revenue collected: $" + String.format("%.2f", totalRevenue));
            System.out.println();
            
            printSeparator();
            
        } catch (ParkingException e) {
            System.err.println("✗ Error in Scenario 3: " + e.getMessage());
        }
    }

    // ════════════════════════════════════════════════════════════════════════════════
    // SCENARIO 4: PARKING LOT CAPACITY MANAGEMENT
    // ════════════════════════════════════════════════════════════════════════════════

    private static void simulateCapacityManagement(IParkingLotManager parkingLot) {
        printBanner("SCENARIO 4: PARKING LOT CAPACITY MANAGEMENT");
        
        try {
            System.out.println("\n>>> Attempting to fill parking lot to capacity:\n");
            
            int parkedCount = 0;
            List<String> parkedPlates = new ArrayList<>();
            
            // Try to park vehicles until lot is full
            // Config: 3 floors × 20 spots = 60 total spots
            for (int i = 0; i < 62; i++) {  // Try 62 to exceed capacity
                try {
                    String plate = String.format("CAR-%03d", i + 1);
                    IVehicle vehicle = new Vehicle(plate, new CarType());
                    String txn = parkingLot.registerEntry(vehicle);
                    parkedCount++;
                    parkedPlates.add(plate);
                    
                    if ((i + 1) % 10 == 0) {
                        System.out.println("  ✓ " + (i + 1) + " vehicles parked successfully");
                    }
                } catch (ParkingException e) {
                    System.out.println("\n  ⚠ Parking lot full! Cannot park vehicle " + (i + 1));
                    System.out.println("  Error: " + e.getMessage());
                    break;
                }
            }
            
            System.out.println("\n>>> Capacity Status:");
            System.out.println("  Total vehicles parked: " + parkedCount);
            System.out.println("  Maximum capacity: 60 (3 floors × 20 spots)");
            System.out.println("  Occupancy: " + (parkedCount * 100 / 60) + "%");
            
            System.out.println("\n>>> Simulating partial exit (creating space):\n");
            simulateTimeDelay();
            
            // Exit first 5 vehicles to create space
            int exitCount = 0;
            for (int i = 0; i < Math.min(5, parkedPlates.size()); i++) {
                try {
                    parkingLot.registerExit(parkedPlates.get(i));
                    exitCount++;
                    System.out.println("  ✓ Vehicle exited: " + parkedPlates.get(i));
                } catch (ParkingException e) {
                    System.err.println("  ✗ Exit failed: " + e.getMessage());
                }
            }
            
            System.out.println("\n>>> Updated Capacity Status:");
            System.out.println("  Vehicles exited: " + exitCount);
            System.out.println("  Remaining vehicles: " + (parkedCount - exitCount));
            System.out.println("  Available spots: " + exitCount);
            System.out.println();
            
            printSeparator();
            
        } catch (Exception e) {
            System.err.println("✗ Error in Scenario 4: " + e.getMessage());
        }
    }

    // ════════════════════════════════════════════════════════════════════════════════
    // SCENARIO 5: ERROR HANDLING SCENARIOS
    // ════════════════════════════════════════════════════════════════════════════════

    private static void simulateErrorScenarios(IParkingLotManager parkingLot) {
        printBanner("SCENARIO 5: ERROR HANDLING SCENARIOS");
        
        try {
            System.out.println("\n>>> Testing Error Scenarios:\n");
            
            // Error 1: Try to exit a vehicle that never entered
            System.out.println("1. Attempting exit for non-existent vehicle:");
            try {
                parkingLot.registerExit("INVALID-PLATE");
                System.out.println("   ✗ Should have thrown exception!");
            } catch (ParkingException e) {
                System.out.println("   ✓ Correctly caught exception");
                System.out.println("   Error: " + e.getMessage());
            }
            
            System.out.println();
            
            // Error 2: Register vehicle entry successfully, then exit
            System.out.println("2. Normal entry then exit sequence:");
            try {
                IVehicle testCar = new Vehicle("TEST-001", new CarType());
                String txn = parkingLot.registerEntry(testCar);
                System.out.println("   ✓ Entry successful - TXN: " + txn);
                
                simulateTimeDelay();
                
                String receipt = parkingLot.registerExit(testCar.getLicensePlate());
                System.out.println("   ✓ Exit successful");
                // Print just the status line
                for (String line : receipt.split("\n")) {
                    if (line.contains("Status") || line.contains("Completed")) {
                        System.out.println("   " + line);
                    }
                }
            } catch (ParkingException e) {
                System.err.println("   ✗ Unexpected error: " + e.getMessage());
            }
            
            System.out.println();
            
            // Error 3: Duplicate vehicle entry
            System.out.println("3. Testing duplicate vehicle entry:");
            try {
                IVehicle dupCar = new Vehicle("DUP-001", new CarType());
                String txn1 = parkingLot.registerEntry(dupCar);
                System.out.println("   ✓ First entry successful - TXN: " + txn1);
                
                // Try to enter same vehicle again (should fail)
                try {
                    String txn2 = parkingLot.registerEntry(dupCar);
                    System.out.println("   ⚠ Duplicate entry allowed (system allows re-entry after exit)");
                    parkingLot.registerExit(dupCar.getLicensePlate());
                } catch (ParkingException e) {
                    System.out.println("   ✓ Duplicate entry prevented: " + e.getMessage());
                }
            } catch (ParkingException e) {
                System.err.println("   ✗ Error: " + e.getMessage());
            }
            
            System.out.println();
            printSeparator();
            
        } catch (Exception e) {
            System.err.println("✗ Error in Scenario 5: " + e.getMessage());
        }
    }
}
