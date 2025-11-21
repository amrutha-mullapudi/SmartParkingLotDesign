
# Smart Parking Lot System - Database Schema Documentation

## Overview

The Smart Parking Lot System database is designed to manage comprehensive parking operations including vehicle tracking, spot allocation, transaction processing, payment handling, and analytics. The schema supports:

- **Multi-floor parking lot** management
- **Real-time spot availability** tracking
- **Dynamic pricing** strategies (Standard, Progressive, Time-Based)
- **Payment processing** with multiple methods
- **Audit logging** for compliance and security
- **Analytics & reporting** capabilities

## Tables Description

### 1. **vehicle_types**
Defines all vehicle type categories and their characteristics.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| vehicle_type_id | INT | PK | Auto-increment unique identifier |
| type_name | VARCHAR(50) | UQ | Type: Bike, Car, Bus, Truck |
| size_category | VARCHAR(20) | | SMALL, MEDIUM, LARGE, EXTRA_LARGE |
| weight_kg | INT | | Average vehicle weight |
| base_hourly_rate | DECIMAL(10,2) | | Base parking rate per hour |
| spots_required | INT | | Number of spots needed (1-2) |
| active | BOOLEAN | | Active/Inactive status |
| created_at | TIMESTAMP | | Record creation time |
| updated_at | TIMESTAMP | | Last modification time |

**Indexes:** `type_name`, `size_category`

**Sample Data:**
- Bike: $1.50/hr, 1 spot, SMALL
- Car: $3.00/hr, 1 spot, MEDIUM
- Bus: $8.00/hr, 1 spot, LARGE
- Truck: $6.00/hr, 2 spots, EXTRA_LARGE

---

### 2. **floors**
Represents physical parking lot floors.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| floor_id | INT | PK | Auto-increment unique identifier |
| floor_number | INT | UQ | Floor number (0, 1, 2...) |
| total_spots | INT | | Total parking spots on floor |
| available_spots | INT | | Currently unoccupied spots |
| active | BOOLEAN | | Floor operational status |
| created_at | TIMESTAMP | | Creation timestamp |
| updated_at | TIMESTAMP | | Last update timestamp |

**Indexes:** `floor_number`, `available_spots`

**Default Configuration:** 3 floors with 20 spots each (60 total spots)

---

### 3. **parking_spots**
Individual parking spot details and real-time status.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| spot_id | INT | PK | Auto-increment unique identifier |
| spot_identifier | VARCHAR(20) | UQ | Spot code: F0-S0, F1-S5, etc. |
| floor_id | INT | FK | Reference to floors table |
| spot_size | VARCHAR(20) | | SMALL, MEDIUM, LARGE, EXTRA_LARGE |
| spot_status | VARCHAR(20) | | AVAILABLE, OCCUPIED, RESERVED |
| current_vehicle_id | INT | | Currently parked vehicle (null if empty) |
| created_at | TIMESTAMP | | Spot creation time |
| updated_at | TIMESTAMP | | Last status change |

**Foreign Keys:** `floor_id` → `floors.floor_id` (CASCADE DELETE)

**Indexes:** 
- `spot_identifier` (lookup by code)
- `floor_id`, `spot_status` (allocation queries)
- `spot_size`, `spot_status` (size-based allocation)

**Status Transitions:**
```
AVAILABLE → OCCUPIED (vehicle entry)
OCCUPIED → AVAILABLE (vehicle exit)
AVAILABLE → RESERVED (maintenance/reservation)
RESERVED → AVAILABLE (maintenance complete)
```

---

### 4. **vehicles**
Registered vehicle information and ownership details.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| vehicle_id | INT | PK | Auto-increment unique identifier |
| license_plate | VARCHAR(50) | UQ | Unique vehicle license plate |
| vehicle_type_id | INT | FK | Reference to vehicle_types |
| owner_name | VARCHAR(100) | | Vehicle owner/operator name |
| owner_contact | VARCHAR(20) | | Owner phone number |
| owner_email | VARCHAR(100) | | Owner email address |
| registration_date | TIMESTAMP | | Vehicle registration date |
| active | BOOLEAN | | Vehicle active/inactive status |
| created_at | TIMESTAMP | | Record creation time |
| updated_at | TIMESTAMP | | Last update time |

**Foreign Keys:** `vehicle_type_id` → `vehicle_types.vehicle_type_id` (RESTRICT DELETE)

**Indexes:** 
- `license_plate` (primary lookup)
- `vehicle_type_id`, `active` (filtering)
- `owner_name` (owner searches)

**Constraints:**
- License plate is unique across system
- Cannot delete vehicle type if vehicles exist
- Owner contact and email optional but recommended

---

### 5. **transactions**
Parking session transactions from entry to exit.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| transaction_id | INT | PK | Auto-increment unique identifier |
| transaction_code | VARCHAR(50) | UQ | Unique code: TXN-YYYYMMDD-XXXXX |
| vehicle_id | INT | FK | Reference to vehicles |
| spot_id | INT | FK | Assigned parking spot |
| entry_timestamp | TIMESTAMP | | Vehicle entry date/time |
| exit_timestamp | TIMESTAMP | | Vehicle exit date/time (null if active) |
| parking_duration_minutes | INT | | Total duration in minutes |
| transaction_status | VARCHAR(20) | | ACTIVE, COMPLETED, CANCELLED |
| entry_gate_id | INT | | Entry gate identifier |
| exit_gate_id | INT | | Exit gate identifier |
| created_at | TIMESTAMP | | Record creation |
| updated_at | TIMESTAMP | | Last update |

**Foreign Keys:**
- `vehicle_id` → `vehicles.vehicle_id` (CASCADE DELETE)
- `spot_id` → `parking_spots.spot_id` (SET NULL on spot delete)

**Indexes:**
- `transaction_code` (lookup)
- `vehicle_id`, `transaction_status` (filtering)
- `entry_timestamp`, `exit_timestamp` (range queries)
- `transaction_status` (active session queries)

**Lifecycle:**
```
Entry → ACTIVE (entry_timestamp set, exit_timestamp NULL)
Exit → COMPLETED (exit_timestamp set, duration calculated)
Cancel → CANCELLED (if needed before exit)
```

---

### 6. **payment_records**
Payment details and fee calculations for transactions.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| payment_id | INT | PK | Auto-increment unique identifier |
| transaction_id | INT | FK | Reference to transactions |
| parking_fee | DECIMAL(10,2) | | Calculated parking fee |
| fee_calculation_method | VARCHAR(50) | | STANDARD, PROGRESSIVE, TIME_BASED |
| hourly_rate | DECIMAL(10,2) | | Rate used for calculation |
| duration_hours | DECIMAL(10,2) | | Parking duration in hours |
| discount_amount | DECIMAL(10,2) | | Applied discount (if any) |
| final_amount_due | DECIMAL(10,2) | | Final payable amount |
| payment_status | VARCHAR(20) | | PENDING, COMPLETED, FAILED, REFUNDED |
| payment_method | VARCHAR(50) | | CASH, CARD, UPI, WALLET, MONTHLY_PASS |
| payment_timestamp | TIMESTAMP | | Payment processing time |
| transaction_reference | VARCHAR(50) | | Payment gateway reference |
| created_at | TIMESTAMP | | Record creation |
| updated_at | TIMESTAMP | | Last update |

**Foreign Keys:** `transaction_id` → `transactions.transaction_id` (CASCADE DELETE)

**Indexes:**
- `payment_status` (filtering pending/completed)
- `transaction_id`, `payment_status` (transaction lookup)
- `payment_timestamp` (revenue queries)

**Fee Calculation Methods:**
1. **STANDARD** - Simple: `duration_hours × hourly_rate`
2. **PROGRESSIVE** - Increases with duration: rates escalate per hour
3. **TIME_BASED** - Peak/off-peak rates with time multipliers

---

### 7. **pricing_rules**
Configurable pricing strategies for different vehicle types.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| rule_id | INT | PK | Auto-increment unique identifier |
| vehicle_type_id | INT | FK | Vehicle type this rule applies to |
| pricing_strategy | VARCHAR(50) | | STANDARD, PROGRESSIVE, TIME_BASED |
| hourly_rate | DECIMAL(10,2) | | Base hourly rate |
| min_charge | DECIMAL(10,2) | | Minimum charge amount |
| max_daily_charge | DECIMAL(10,2) | | Maximum charge per 24 hours |
| night_multiplier | DECIMAL(5,2) | | Rate multiplier for night (0-24hrs) |
| peak_multiplier | DECIMAL(5,2) | | Rate multiplier for peak hours |
| peak_start_hour | INT | | Peak hours start (0-23) |
| peak_end_hour | INT | | Peak hours end (0-23) |
| active | BOOLEAN | | Rule active status |
| effective_from | DATE | | Rule effective start date |
| effective_to | DATE | | Rule end date (null = ongoing) |
| created_at | TIMESTAMP | | Creation timestamp |
| updated_at | TIMESTAMP | | Last update timestamp |

**Foreign Keys:** `vehicle_type_id` → `vehicle_types.vehicle_type_id` (CASCADE DELETE)

**Indexes:** `vehicle_type_id`, `pricing_strategy`, `effective_from`

**Example Configuration:**
```
Car (PROGRESSIVE strategy):
- Base Rate: $3.00/hr
- Min Charge: $2.00
- Max Daily: $50.00
- Night Multiplier: 0.80 (22:00-06:00)
- Peak Multiplier: 1.50 (09:00-18:00)
```

---

### 8. **allocation_history**
Tracks spot allocation decisions for auditing and optimization.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| allocation_id | INT | PK | Auto-increment unique identifier |
| transaction_id | INT | FK | Reference to transaction |
| allocation_strategy | VARCHAR(50) | | LOAD_BALANCED, SIZE_OPTIMIZED, NEAREST_TO_EXIT |
| requested_spot_size | VARCHAR(20) | | Requested spot size |
| allocated_spot_id | INT | FK | Actually allocated spot |
| allocation_score | DECIMAL(10,2) | | Algorithm score/priority |
| alternatives_count | INT | | Number of available alternatives |
| allocation_time_ms | INT | | Time taken in milliseconds |
| success | BOOLEAN | | Allocation success status |
| created_at | TIMESTAMP | | Allocation timestamp |

**Foreign Keys:**
- `transaction_id` → `transactions.transaction_id` (CASCADE DELETE)
- `allocated_spot_id` → `parking_spots.spot_id` (CASCADE DELETE)

**Indexes:** `allocation_strategy`, `created_at`, `success`

**Allocation Strategies:**
1. **LOAD_BALANCED** - Distributes vehicles evenly across floors
2. **SIZE_OPTIMIZED** - Minimizes spot size waste
3. **NEAREST_TO_EXIT** - Allocates closest to exit gate

---

### 9. **users**
Parking lot staff and system operators.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| user_id | INT | PK | Auto-increment unique identifier |
| username | VARCHAR(50) | UQ | Login username |
| email | VARCHAR(100) | UQ | User email (unique) |
| full_name | VARCHAR(100) | | Full name |
| user_role | VARCHAR(50) | | ADMIN, OPERATOR, MANAGER, VIEWER |
| password_hash | VARCHAR(255) | | Bcrypt hashed password |
| phone | VARCHAR(20) | | Contact phone number |
| active | BOOLEAN | | Account active status |
| last_login | TIMESTAMP | | Last successful login |
| created_at | TIMESTAMP | | Account creation date |
| updated_at | TIMESTAMP | | Last account update |

**Indexes:** `username`, `email`, `user_role`

**User Roles:**
- **ADMIN** - Full system access, configuration changes
- **OPERATOR** - Gate operations, vehicle entry/exit
- **MANAGER** - Revenue reports, parking management
- **VIEWER** - Read-only analytics and reports

---

### 10. **audit_logs**
Comprehensive activity logging for compliance and debugging.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| log_id | INT | PK | Auto-increment unique identifier |
| user_id | INT | FK | User who performed action |
| action_type | VARCHAR(100) | | ENTRY, EXIT, PAYMENT, CONFIG_CHANGE |
| entity_type | VARCHAR(50) | | VEHICLE, TRANSACTION, PAYMENT, SPOT |
| entity_id | INT | | ID of affected entity |
| details | JSON | | Additional action details |
| status | VARCHAR(20) | | SUCCESS, FAILURE, PARTIAL |
| error_message | TEXT | | Error message if failed |
| ip_address | VARCHAR(45) | | Client IP address (IPv4/IPv6) |
| created_at | TIMESTAMP | | Log entry timestamp |

**Foreign Keys:** `user_id` → `users.user_id` (SET NULL on user delete)

**Indexes:** `action_type`, `entity_type`, `created_at`, `user_id`

**JSON Details Example:**
```json
{
  "license_plate": "KA-01-AB-1001",
  "spot": "F0-S5",
  "gate": 1,
  "duration_min": 150
}
```

---

### 11. **parking_lot_stats**
Daily and hourly aggregated statistics for analytics.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| stat_id | INT | PK | Auto-increment unique identifier |
| stat_date | DATE | | Date of statistics |
| stat_hour | INT | | Hour (0-23), null for daily |
| total_vehicles_entered | INT | | Total entries |
| total_vehicles_exited | INT | | Total exits |
| peak_occupancy | INT | | Maximum occupancy during period |
| average_occupancy | INT | | Average occupancy during period |
| total_revenue | DECIMAL(15,2) | | Total revenue for period |
| average_parking_duration_minutes | INT | | Average parking duration |
| failed_entries | INT | | Number of failed entry attempts |
| created_at | TIMESTAMP | | Record creation |
| updated_at | TIMESTAMP | | Last update |

**Indexes:** `stat_date`, `stat_hour`

**Unique Constraint:** One record per (stat_date, stat_hour) combination

---

### 12. **configuration**
System-wide configuration parameters.

| Column | Type | Key | Description |
|--------|------|-----|-------------|
| config_id | INT | PK | Auto-increment unique identifier |
| config_key | VARCHAR(100) | UQ | Configuration parameter name |
| config_value | VARCHAR(500) | | Parameter value |
| config_type | VARCHAR(50) | | STRING, INT, BOOLEAN, DECIMAL |
| description | TEXT | | Parameter description |
| is_modifiable | BOOLEAN | | Can be changed by admins |
| created_at | TIMESTAMP | | Creation timestamp |
| updated_at | TIMESTAMP | | Last modification |

**Indexes:** `config_key`

**Default Parameters:**
- `TOTAL_FLOORS`: 3 (non-modifiable)
- `SPOTS_PER_FLOOR`: 20 (non-modifiable)
- `ALLOCATION_STRATEGY`: LOAD_BALANCED (modifiable)
- `FEE_CALCULATION_STRATEGY`: PROGRESSIVE (modifiable)
- `GRACE_PERIOD_MINUTES`: 15 (modifiable)

---

## Entity Relationships

### Relationship Diagram

```
vehicle_types (1) ──→ (N) vehicles
                 ──→ (N) pricing_rules

vehicles (1) ──────────→ (N) transactions
           ──────────→ (N) allocation_history

floors (1) ────────────→ (N) parking_spots

parking_spots (1) ────→ (N) transactions

transactions (1) ──────→ (N) payment_records
              ──────────→ (N) allocation_history

users (1) ─────────────→ (N) audit_logs

[All tables] ──────────→ (N) audit_logs
```

### Referential Integrity Rules

| Parent | Child | Action |
|--------|-------|--------|
| vehicle_types | vehicles | RESTRICT (can't delete type if vehicles exist) |
| vehicle_types | pricing_rules | CASCADE (delete rules when type deleted) |
| vehicles | transactions | CASCADE (delete transactions when vehicle deleted) |
| floors | parking_spots | CASCADE (delete spots when floor deleted) |
| parking_spots | transactions | SET NULL (keep transaction, remove spot reference) |
| transactions | payment_records | CASCADE (delete payments when transaction deleted) |
| transactions | allocation_history | CASCADE (delete history when transaction deleted) |
| parking_spots | allocation_history | CASCADE (delete history when spot deleted) |
| users | audit_logs | SET NULL (keep log, remove user reference) |
