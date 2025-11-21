# Smart Parking Lot System - Complete Project Overview

## 📋 Project Summary

**Smart Parking Lot System** is a comprehensive, production-ready Java-based parking management solution with enterprise-grade architecture. It manages multi-floor parking operations including vehicle tracking, dynamic spot allocation, automated fee calculation, and detailed analytics.

### ✨ Key Features
- **Multi-floor management** with real-time occupancy tracking
- **4 vehicle types** (Bike, Car, Bus, Truck) with different size/weight requirements
- **3 intelligent spot allocation strategies** (Load Balanced, Size Optimized, Nearest to Exit)
- **3 dynamic pricing strategies** (Standard, Progressive, Time-Based)
- **Transaction management** with entry/exit tracking
- **Payment processing** with multiple payment methods
- **Comprehensive audit logging** for compliance
- **Analytics & reporting** with statistics aggregation
- **Database-backed persistence** (MySQL) with 12 optimized tables
- **Exception-driven error handling** across all layers
- **Complete driver code** with 5 real-world scenarios

### 🏗️ Architecture Layers
```
APPLICATION LAYER    → Facades (IParkingLotManager, IEntryManager, IExitManager)
    ↓
BUSINESS LOGIC      → Entities (Vehicle, Spot, Floor, Transaction, Payment)
    ↓
STRATEGY LAYER      → Pluggable algorithms (Allocators, Fee Calculators)
    ↓
MANAGER LAYER       → Services (SpotManager, TransactionManager, PaymentProcessor)
    ↓
PERSISTENCE LAYER   → Database (MySQL with 12 optimized tables)
```

---

## 📁 Project Structure

```
SmartParkingLotDesign-1/
├── src/main/java/com/smartparking/
│   ├── config/
│   │   └── ParkingLotConfig.java ..................... Factory for system initialization
│   ├── constant/
│   │   ├── PaymentMethod.java
│   │   ├── PaymentStatus.java
│   │   ├── SpotSize.java
│   │   ├── SpotStatus.java
│   │   ├── TransactionStatus.java
│   │   ├── VehicleSize.java
│   │   └── VehicleWeight.java
│   ├── controller/
│   │   ├── IParkingLotManager.java .................. Facade interface
│   │   └── ParkingLotManager.java ................... Main orchestrator
│   ├── core/
│   │   ├── entity/
│   │   │   ├── IFloor.java
│   │   │   ├── IParkingSpot.java
│   │   │   ├── IPaymentRecord.java
│   │   │   ├── ITransaction.java
│   │   │   ├── IVehicle.java
│   │   │   └── IVehicleType.java
│   │   └── impl/
│   │       ├── Floor.java
│   │       ├── ParkingSpot.java
│   │       ├── PaymentRecord.java
│   │       ├── Transaction.java
│   │       ├── Vehicle.java
│   │       └── VehicleType.java
│   ├── exception/
│   │   ├── InsufficientCapacityException.java
│   │   ├── InvalidVehicleException.java
│   │   ├── NoSpotAvailableException.java
│   │   ├── ParkingException.java
│   │   ├── PaymentFailedException.java
│   │   └── TransactionException.java
│   ├── helper/
│   │   ├── IPricingRule.java
│   │   ├── IReceipt.java
│   │   ├── PricingContext.java
│   │   └── Receipt.java
│   ├── manager/
│   │   ├── payment/
│   │   │   ├── IPaymentProcessor.java
│   │   │   └── PaymentProcessor.java
│   │   ├── registry/
│   │   │   ├── IVehicleTypeRegistry.java
│   │   │   └── VehicleTypeRegistry.java
│   │   ├── spot/
│   │   │   ├── IParkingSpotManager.java
│   │   │   └── ParkingSpotManager.java
│   │   └── transaction/
│   │       ├── ITransactionManager.java
│   │       └── TransactionManager.java
│   ├── strategy/
│   │   ├── allocator/
│   │   │   ├── ISpotAllocator.java .................. Strategy interface
│   │   │   ├── LoadBalancedAllocator.java .......... Even distribution
│   │   │   ├── NearestToExitAllocator.java ........ Closest to exit
│   │   │   └── SizeOptimizedAllocator.java ........ Minimal waste
│   │   └── feecalculator/
│   │       ├── IFeeCalculator.java .................. Strategy interface
│   │       ├── StandardFeeCalculator.java ......... Linear calculation
│   │       ├── ProgressiveFeeCalculator.java ...... Escalating rates
│   │       └── TimeBasedFeeCalculator.java ........ Peak/off-peak
│   ├── vehicletype/
│   │   ├── BikeType.java ............................ 1 spot, SMALL
│   │   ├── BusType.java ............................. 1 spot, LARGE
│   │   ├── CarType.java ............................. 1 spot, MEDIUM
│   │   └── TruckType.java ........................... 2 spots, EXTRA_LARGE
│   ├── workflow/
│   │   ├── EntryManager.java ........................ Vehicle entry
│   │   ├── ExitManager.java ......................... Vehicle exit
│   │   ├── IEntryManager.java
│   │   └── IExitManager.java
│   └── Main.java ................................... Driver code (5 scenarios)
│
├── database/
│   ├── 01_create_schema.sql ......................... Database creation (12 tables)
│   ├── 02_insert_sample_data.sql ................... Sample data insertion
│   ├── 03_create_indexes_views.sql ................ Indexes & 7 views
│   └── DATABASE_SCHEMA_README.md ................... Schema documentation
│
└── README.md ....................................... This file
```

---

## Core Interfaces & Classes Overview

```
┌─────────────────────────────────────────────────────────────────┐
│                       LAYER ARCHITECTURE                         │
├─────────────────────────────────────────────────────────────────┤
│                                                                 │
│  ┌────────────────────────────────────────────────────────┐    │
│  │              APPLICATION LAYER                        │    │
│  │  • IParkingLotManager (Facade/Orchestrator)          │    │
│  │  • IEntryManager                                      │    │
│  │  • IExitManager                                       │    │
│  └────────────────────────────────────────────────────────┘    │
│                          △                                      │
│                          │                                      │
│  ┌────────────────────────────────────────────────────────┐    │
│  │              BUSINESS LOGIC LAYER                      │    │
│  │  • IVehicle (Entity)                                  │    │
│  │  • IVehicleType (Entity)                              │    │
│  │  • IFloor (Entity)                                    │    │
│  │  • IParkingSpot (Entity)                              │    │
│  │  • ITransaction (Entity)                              │    │
│  │  • IPaymentRecord (Entity)                            │    │
│  └────────────────────────────────────────────────────────┘    │
│                          △                                      │
│                          │                                      │
│  ┌────────────────────────────────────────────────────────┐    │
│  │              STRATEGY LAYER (Pluggable)               │    │
│  │  • ISpotAllocator (Strategy Pattern)                  │    │
│  │  • IFeeCalculator (Strategy Pattern)                  │    │
│  └────────────────────────────────────────────────────────┘    │
│                          △                                      │
│                          │                                      │
│  ┌────────────────────────────────────────────────────────┐    │
│  │              MANAGER/SERVICE LAYER                     │    │
│  │  • IParkingSpotManager                                │    │
│  │  • ITransactionManager                                │    │
│  │  • IPaymentProcessor                                  │    │
│  │  • IVehicleTypeRegistry                               │    │
│  └────────────────────────────────────────────────────────┘    │
│                                                                 │
└─────────────────────────────────────────────────────────────────┘
```

---

## 1. CORE ENTITIES (Domain Model)

### Vehicle Hierarchy

```
┌─────────────────────────────────────┐
│ IVehicleType (Template/Definition)  │
├─────────────────────────────────────┤
│ + getVehicleTypeId(): String        │
│ + getTypeName(): String             │
│ + getSize(): VehicleSize            │
│ + getWeight(): VehicleWeight        │
│ + getWeightInKg(): float            │
│ + getSpotsRequired(): int           │
│ + getFloorEligibility(): int[]      │
│ + getSpotSizeRequirement(): SpotSize│
│ + canFitInSpot(SpotSize): boolean   │
│ + canParkOnFloor(int): boolean      │
│ + getPricingRuleId(): String        │
└─────────────────────────────────────┘
         △ △ △ △
         │ │ │ │
    BikeType CarType BusType TruckType


┌─────────────────────────────────────┐
│ IVehicle (Individual Instance)      │
├─────────────────────────────────────┤
│ + getVehicleId(): String            │
│ + getLicensePlate(): String         │
│ + getVehicleType(): IVehicleType    │
│ + getOwnerName(): String            │
│ + getOwnerId(): String              │
│ + getCurrentSpot(): IParkingSpot    │
│ + getEntryTime(): DateTime          │
│ + parkAtSpot(spot): void            │
│ + parkAtConsecutiveSpots(spots):    │
│ + unparkFromSpot(): void            │
│ + getColor(): String                │
└─────────────────────────────────────┘

Relationship: Many IVehicle : 1 IVehicleType
(50 Cars can be CarType)
```

### Parking Infrastructure

```
┌─────────────────────────────────────┐
│ IParkingSpot                        │
├─────────────────────────────────────┤
│ + getSpotId(): String               │
│ + getSpotSize(): SpotSize           │
│ + getMaxWeightCapacity(): float     │
│ + getCurrentVehicleId(): String     │
│ + getStatus(): SpotStatus           │
│ + getFloorId(): String              │
│ + isAvailable(): boolean            │
│ + canAccommodate(IVehicleType):bool │
│ + occupy(vehicleId, type): void     │
│ + vacate(): void                    │
│ + setMaintenance(bool): void        │
│ + getSpotInfo(): String             │
└─────────────────────────────────────┘


┌─────────────────────────────────────┐
│ IFloor                              │
├─────────────────────────────────────┤
│ + getFloorId(): String              │
│ + getFloorNumber(): int             │
│ + getTotalSpots(): int              │
│ + getAvailableSpots(): int          │
│ + getOccupiedSpots(): int           │
│ + getAllSpots(): List<IParkingSpot> │
│ + getSpotsBySize(SpotSize): List    │
│ + getConsecutiveSpots(int,Size):    │
│ + getAvailableSpotsByVehicleType(): │
│ + getCurrentFloorLoad(): float      │
│ + getMaxFloorWeightCapacity():float │
│ + getAllowedVehicleTypes(): List    │
│ + canAccommodateAdditional(type):   │
│ + isUnderMaintenance(): boolean     │
│ + setMaintenance(bool): void        │
│ + addParkingSpot(spot): void        │
│ + getParkingSpot(spotId): Spot      │
└─────────────────────────────────────┘

Relationship: IFloor contains Many IParkingSpot
```

### Transactions & Payments

```
┌─────────────────────────────────────┐
│ ITransaction                        │
├─────────────────────────────────────┤
│ + getTransactionId(): String        │
│ + getVehicleId(): String            │
│ + getSpotIds(): List<String>        │
│ + getFloorId(): String              │
│ + getEntryTime(): DateTime          │
│ + getExitTime(): DateTime           │
│ + getDuration(): float              │
│ + getAmountDue(): float             │
│ + getPaymentId(): String            │
│ + getStatus(): TransactionStatus    │
│ + setExitTime(DateTime): void       │
│ + setAmountDue(float): void         │
│ + setPaymentId(String): void        │
│ + complete(): void                  │
└─────────────────────────────────────┘


┌─────────────────────────────────────┐
│ IPaymentRecord                      │
├─────────────────────────────────────┤
│ + getPaymentId(): String            │
│ + getTransactionId(): String        │
│ + getAmount(): float                │
│ + getPaymentMethod(): PaymentMethod │
│ + getStatus(): PaymentStatus        │
│ + getGatewayChargeId(): String      │
│ + getProcessedAt(): DateTime        │
│ + getReceipt(): String              │
└─────────────────────────────────────┘
```

---

## 2. STRATEGIES (Pluggable/Replaceable)

```
┌──────────────────────────────────────┐
│ ISpotAllocator (Strategy)            │
├──────────────────────────────────────┤
│ + allocateSpot(                      │
│     vehicleType: IVehicleType,       │
│     availableSpots: List,            │
│     parkingLot: IParkingLot          │
│   ): IParkingSpot                    │
└──────────────────────────────────────┘
         △ △ △ △ △
         │ │ │ │ │
    WeightAware
    LoadBalanced
    SizeOptimized
    NearestToExit
    CapacityFirst


┌──────────────────────────────────────┐
│ IFeeCalculator (Strategy)            │
├──────────────────────────────────────┤
│ + calculateFee(                      │
│     duration: float,                 │
│     vehicleType: IVehicleType,       │
│     pricingRule: IPricingRule        │
│   ): float                           │
└──────────────────────────────────────┘
         △ △ △
         │ │ │
    StandardFeeCalculator
    ProgressiveFeeCalculator
    TimeBasedFeeCalculator
```

---

## 3. MANAGERS/SERVICES

```
┌───────────────────────────────────────┐
│ IParkingSpotManager                   │
├───────────────────────────────────────┤
│ - spotAllocator: ISpotAllocator       │
│ - parkingLot: IParkingLot             │
│                                       │
│ + findAvailableSpot(IVehicleType):    │
│ + findConsecutiveSpots(IVehicleType): │
│ + allocateSpot(IVehicleType):         │
│ + allocateConsecutiveSpots(type):     │
│ + validateSpot(vehicle, spot): bool   │
│ + occupySpot(vehicle, spot): void     │
│ + releaseSpot(spot): void             │
│ + setSpotAllocator(allocator): void   │
│ + getFloor(floorId): IFloor           │
│ + getAllFloors(): List<IFloor>        │
└───────────────────────────────────────┘


┌───────────────────────────────────────┐
│ ITransactionManager                   │
├───────────────────────────────────────┤
│ + createTransaction(vehicle, spot):   │
│ + getActiveTransaction(plate): Tx     │
│ + completeTransaction(txId): void     │
│ + getTransactionHistory(plate):       │
│ + recordExit(txId, exitTime): void    │
└───────────────────────────────────────┘


┌───────────────────────────────────────┐
│ IPaymentProcessor                     │
├───────────────────────────────────────┤
│ + processPayment(                     │
│     amount: float,                    │
│     method: PaymentMethod,            │
│     details: {...}                    │
│   ): IPaymentRecord                   │
│ + processRefund(paymentId): void      │
│ + getPaymentHistory(txId):            │
└───────────────────────────────────────┘


┌───────────────────────────────────────┐
│ IVehicleTypeRegistry                  │
├───────────────────────────────────────┤
│ + registerVehicleType(type): void     │
│ + getVehicleType(typeId): IVehicleType│
│ + getAllVehicleTypes(): List          │
│ + isTypeSupported(typeId): boolean    │
└───────────────────────────────────────┘
```

---

## 4. HIGH-LEVEL CONTROLLERS/FACADES

```
┌──────────────────────────────────────────┐
│ IParkingLotManager (Facade)              │
│ (Main Orchestrator - Entry Point)        │
├──────────────────────────────────────────┤
│ - floors: Map<String, IFloor>            │
│ - entryManager: IEntryManager            │
│ - exitManager: IExitManager              │
│ - spotManager: IParkingSpotManager       │
│ - transactionManager: ITransactionManager│
│ - paymentProcessor: IPaymentProcessor    │
│ - vehicleTypeRegistry: IVehicleRegistry  │
│                                          │
│ + checkInVehicle(vehicle): IParkingSpot │
│ + checkOutVehicle(licensePlate): Receipt│
│ + getAvailableSpots(): int              │
│ + getFloorInfo(floorId): String         │
│ + getTransactionHistory(plate): List    │
│ + getSystemStatus(): String             │
└──────────────────────────────────────────┘


┌──────────────────────────────────────────┐
│ IEntryManager                            │
├──────────────────────────────────────────┤
│ - spotManager: IParkingSpotManager       │
│ - transactionManager: ITransactionManager│
│ - vehicleTypeRegistry: IVehicleRegistry  │
│                                          │
│ + checkInVehicle(vehicle): IParkingSpot │
│ + validateVehicle(vehicle): boolean     │
│ + findSpotForVehicle(vehicle): Spot(s)  │
│ + createEntryTransaction(vehicle,spot):│
└──────────────────────────────────────────┘


┌──────────────────────────────────────────┐
│ IExitManager                             │
├──────────────────────────────────────────┤
│ - transactionManager: ITransactionManager│
│ - spotManager: IParkingSpotManager       │
│ - feeCalculator: IFeeCalculator          │
│ - paymentProcessor: IPaymentProcessor    │
│                                          │
│ + checkOutVehicle(plate): Receipt       │
│ + validateExit(txId): boolean           │
│ + calculateCharges(txId): float         │
│ + processExit(txId): void               │
│ + generateReceipt(txId): String         │
└──────────────────────────────────────────┘
```

---

## 5. HELPER CLASSES & ENUMS

```
┌──────────────────────────────────────┐
│ Enum: VehicleSize                    │
├──────────────────────────────────────┤
│ SMALL (Bike, Scooter)               │
│ MEDIUM (Car, SUV)                   │
│ LARGE (Bus)                         │
│ EXTRA_LARGE (Truck)                 │
└──────────────────────────────────────┘


┌──────────────────────────────────────┐
│ Enum: VehicleWeight                  │
├──────────────────────────────────────┤
│ LIGHT (< 500 kg)                    │
│ MEDIUM (500-2000 kg)                │
│ HEAVY (> 2000 kg)                   │
└──────────────────────────────────────┘


┌──────────────────────────────────────┐
│ Enum: SpotSize                       │
├──────────────────────────────────────┤
│ SMALL                               │
│ MEDIUM                              │
│ LARGE                               │
│ EXTRA_LARGE                         │
└──────────────────────────────────────┘


┌──────────────────────────────────────┐
│ Enum: SpotStatus                     │
├──────────────────────────────────────┤
│ AVAILABLE                           │
│ OCCUPIED                            │
│ RESERVED                            │
│ MAINTENANCE                         │
└──────────────────────────────────────┘


┌──────────────────────────────────────┐
│ Enum: TransactionStatus              │
├──────────────────────────────────────┤
│ IN_PROGRESS                         │
│ COMPLETED                           │
│ CANCELLED                           │
│ DISPUTE                             │
└──────────────────────────────────────┘


┌──────────────────────────────────────┐
│ Enum: PaymentStatus                  │
├──────────────────────────────────────┤
│ PENDING                             │
│ SUCCESS                             │
│ FAILED                              │
│ REFUNDED                            │
└──────────────────────────────────────┘


┌──────────────────────────────────────┐
│ Enum: PaymentMethod                  │
├──────────────────────────────────────┤
│ CARD (Credit/Debit)                │
│ CASH                                │
│ WALLET                              │
│ DIGITAL_PAYMENT                     │
└──────────────────────────────────────┘


┌────────────────────────────────────────┐
│ IPricingRule (Configuration)           │
├────────────────────────────────────────┤
│ + getVehicleType(): IVehicleType       │
│ + getHourlyRate(): float               │
│ + getMinimumCharge(): float            │
│ + getGracePeriod(): int (minutes)      │
│ + getMaxCharge(): float (daily max)    │
│ + getWeekendMultiplier(): float        │
└────────────────────────────────────────┘


┌────────────────────────────────────────┐
│ IReceipt (Data Object)                 │
├────────────────────────────────────────┤
│ + getTransactionId(): String           │
│ + getLicensePlate(): String            │
│ + getVehicleType(): String             │
│ + getEntryTime(): DateTime             │
│ + getExitTime(): DateTime              │
│ + getDuration(): String                │
│ + getSpotUsed(): String                │
│ + getAmountDue(): float                │
│ + getAmountPaid(): float               │
│ + getPaymentMethod(): PaymentMethod    │
│ + getStatus(): String                  │
│ + getReceiptText(): String             │
└────────────────────────────────────────┘
```

---

## 6. COMPLETE CLASS RELATIONSHIP DIAGRAM

```
┌───────────────────────────────────────────────────────────────┐
│                  COMPLETE SYSTEM VIEW                         │
└───────────────────────────────────────────────────────────────┘

                    IParkingLotManager
                          │
            ┌─────────────┼─────────────┐
            │             │             │
        IEntry        IExit          IParkingLot
        Manager       Manager        (contains)
            │             │             │
            └─────────────┼─────────────┘
                          │
        ┌─────────────────┼─────────────────┐
        │                 │                 │
   IParkingSpotManager  ITransaction   IPaymentProcessor
        │                Manager           │
        │                 │                 │
        ├──→ ISpotAllocator (Strategy)     │
        │                 │                 │
        ├──→ IFloor        ├──→ IFeeCalculator (Strategy)
        │                 │                 │
        ├──→ IParkingSpot  └──→ IPaymentRecord
        │
        ├──→ IVehicleType
        │
        └──→ IVehicle (uses IVehicleType)
```

---

## 7. QUICK REFERENCE TABLE

```
┌──────────────────────────────────────────────────────────────────┐
│              INTERFACE QUICK REFERENCE                           │
├──────────┬──────────────────────────┬──────────────────────────┤
│ Category │ Interface                │ Purpose                  │
├──────────┼──────────────────────────┼──────────────────────────┤
│ Domain   │ IVehicleType             │ Vehicle type definition  │
│          │ IVehicle                 │ Individual vehicle       │
│          │ IParkingSpot             │ Single parking spot      │
│          │ IFloor                   │ Floor with spots         │
│          │ ITransaction             │ Parking transaction      │
│          │ IPaymentRecord           │ Payment details          │
├──────────┼──────────────────────────┼──────────────────────────┤
│ Strategy │ ISpotAllocator           │ Spot selection algorithm │
│          │ IFeeCalculator           │ Fee calculation logic    │
├──────────┼──────────────────────────┼──────────────────────────┤
│ Manager  │ IParkingSpotManager      │ Spot finding & occupy    │
│          │ ITransactionManager      │ Transaction lifecycle    │
│          │ IPaymentProcessor        │ Payment processing       │
│          │ IVehicleTypeRegistry     │ Type registration        │
├──────────┼──────────────────────────┼──────────────────────────┤
│ Facade   │ IParkingLotManager       │ Main orchestrator        │
│          │ IEntryManager            │ Vehicle entry workflow   │
│          │ IExitManager             │ Vehicle exit workflow    │
├──────────┼──────────────────────────┼──────────────────────────┤
│ Helper   │ IPricingRule             │ Pricing configuration    │
│          │ IReceipt                 │ Receipt details          │
│          │ Enums (Size, Weight,     │ Constants & states       │
│          │ Status, etc.)            │                          │
└──────────┴──────────────────────────┴──────────────────────────┘
```

---

## 8. IMPLEMENTATION CLASSES (Examples)

```
Concrete Implementations (to be created):

Domain:
  • Vehicle implements IVehicle
  • BikeType implements IVehicleType
  • CarType implements IVehicleType
  • BusType implements IVehicleType
  • TruckType implements IVehicleType
  • ParkingSpot implements IParkingSpot
  • Floor implements IFloor
  • Transaction implements ITransaction
  • PaymentRecord implements IPaymentRecord

Strategy:
  • WeightAwareAllocator implements ISpotAllocator
  • LoadBalancedAllocator implements ISpotAllocator
  • SizeOptimizedAllocator implements ISpotAllocator
  • NearestToExitAllocator implements ISpotAllocator
  • StandardFeeCalculator implements IFeeCalculator
  • ProgressiveFeeCalculator implements IFeeCalculator

Manager:
  • ParkingSpotManager implements IParkingSpotManager
  • TransactionManager implements ITransactionManager
  • PaymentProcessor implements IPaymentProcessor
  • VehicleTypeRegistry implements IVehicleTypeRegistry

Facade:
  • ParkingLotManager implements IParkingLotManager
  • EntryManager implements IEntryManager
  • ExitManager implements IExitManager

Helper:
  • PricingRule implements IPricingRule
  • Receipt implements IReceipt
```

---

## 9. DEPENDENCY FLOW

```
Entry Flow:
──────────
User
  ↓
IParkingLotManager
  ├─→ IEntryManager
  │    ├─→ IVehicleTypeRegistry (validate type)
  │    ├─→ IParkingSpotManager (find spot)
  │    │    ├─→ IFloor (query spots)
  │    │    ├─→ IParkingSpot (validate)
  │    │    └─→ ISpotAllocator (pick best)
  │    └─→ ITransactionManager (create tx)
  │
  └─→ IParkingSpot (occupy)
       └─→ IFloor (update load)


Exit Flow:
─────────
User
  ↓
IParkingLotManager
  ├─→ IExitManager
  │    ├─→ ITransactionManager (get active tx)
  │    ├─→ IFeeCalculator (calculate fee)
  │    ├─→ IPaymentProcessor (process payment)
  │    ├─→ IParkingSpotManager (release spot)
  │    │    └─→ IParkingSpot (vacate)
  │    └─→ ITransactionManager (complete tx)
  │
  └─→ Receipt generated
```
