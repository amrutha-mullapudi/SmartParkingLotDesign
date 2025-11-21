-- ════════════════════════════════════════════════════════════════════════════════
-- SMART PARKING LOT SYSTEM - DATABASE SCHEMA
-- Database Name: smart_parking_lot
-- Version: 1.0
-- Created: November 21, 2025
-- ════════════════════════════════════════════════════════════════════════════════

-- Drop existing database if exists (use with caution in production)
-- DROP DATABASE IF EXISTS smart_parking_lot;

-- Create database
CREATE DATABASE IF NOT EXISTS smart_parking_lot
CHARACTER SET utf8mb4
COLLATE utf8mb4_unicode_ci;

USE smart_parking_lot;

-- ════════════════════════════════════════════════════════════════════════════════
-- TABLE 1: VEHICLE_TYPES
-- Purpose: Store all vehicle type definitions (Bike, Car, Bus, Truck, etc.)
-- ════════════════════════════════════════════════════════════════════════════════

CREATE TABLE vehicle_types (
    vehicle_type_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique vehicle type identifier',
    type_name VARCHAR(50) NOT NULL UNIQUE COMMENT 'Type name: Bike, Car, Bus, Truck',
    size_category VARCHAR(20) NOT NULL COMMENT 'Size: SMALL, MEDIUM, LARGE, EXTRA_LARGE',
    weight_kg INT NOT NULL COMMENT 'Average weight in kilograms',
    base_hourly_rate DECIMAL(10, 2) NOT NULL COMMENT 'Base parking rate per hour',
    spots_required INT NOT NULL DEFAULT 1 COMMENT 'Number of spots required',
    active BOOLEAN DEFAULT TRUE COMMENT 'Whether this vehicle type is active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation timestamp',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Record update timestamp',
    
    INDEX idx_type_name (type_name),
    INDEX idx_size_category (size_category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Vehicle type definitions with pricing and characteristics';

-- ════════════════════════════════════════════════════════════════════════════════
-- TABLE 2: FLOORS
-- Purpose: Store parking lot floor information
-- ════════════════════════════════════════════════════════════════════════════════

CREATE TABLE floors (
    floor_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique floor identifier',
    floor_number INT NOT NULL UNIQUE COMMENT 'Floor number (0-indexed)',
    total_spots INT NOT NULL COMMENT 'Total parking spots on this floor',
    available_spots INT NOT NULL COMMENT 'Currently available spots',
    active BOOLEAN DEFAULT TRUE COMMENT 'Whether this floor is operational',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Floor creation date',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last update time',
    
    INDEX idx_floor_number (floor_number),
    INDEX idx_available_spots (available_spots)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Physical parking lot floors';

-- ════════════════════════════════════════════════════════════════════════════════
-- TABLE 3: PARKING_SPOTS
-- Purpose: Store individual parking spot information
-- ════════════════════════════════════════════════════════════════════════════════

CREATE TABLE parking_spots (
    spot_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique spot identifier',
    spot_identifier VARCHAR(20) NOT NULL UNIQUE COMMENT 'Spot code: F0-S0, F1-S5, etc.',
    floor_id INT NOT NULL COMMENT 'Floor where spot is located',
    spot_size VARCHAR(20) NOT NULL COMMENT 'Spot size: SMALL, MEDIUM, LARGE, EXTRA_LARGE',
    spot_status VARCHAR(20) NOT NULL DEFAULT 'AVAILABLE' COMMENT 'AVAILABLE, OCCUPIED, RESERVED',
    current_vehicle_id INT COMMENT 'Currently parked vehicle ID (null if available)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Spot creation date',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last status update',
    
    CONSTRAINT fk_parking_spots_floor FOREIGN KEY (floor_id) REFERENCES floors(floor_id) ON DELETE CASCADE,
    INDEX idx_spot_identifier (spot_identifier),
    INDEX idx_floor_id (floor_id),
    INDEX idx_spot_status (spot_status),
    INDEX idx_spot_size (spot_size)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Individual parking spot information with status tracking';

-- ════════════════════════════════════════════════════════════════════════════════
-- TABLE 4: VEHICLES
-- Purpose: Store registered vehicle information
-- ════════════════════════════════════════════════════════════════════════════════

CREATE TABLE vehicles (
    vehicle_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique vehicle identifier',
    license_plate VARCHAR(50) NOT NULL UNIQUE COMMENT 'Vehicle license plate number',
    vehicle_type_id INT NOT NULL COMMENT 'Reference to vehicle type',
    owner_name VARCHAR(100) COMMENT 'Vehicle owner name',
    owner_contact VARCHAR(20) COMMENT 'Vehicle owner contact number',
    owner_email VARCHAR(100) COMMENT 'Vehicle owner email',
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Vehicle registration date',
    active BOOLEAN DEFAULT TRUE COMMENT 'Whether vehicle is active',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last update',
    
    CONSTRAINT fk_vehicles_type FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_types(vehicle_type_id) ON DELETE RESTRICT,
    INDEX idx_license_plate (license_plate),
    INDEX idx_vehicle_type (vehicle_type_id),
    INDEX idx_owner_name (owner_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Registered vehicle information';

-- ════════════════════════════════════════════════════════════════════════════════
-- TABLE 5: TRANSACTIONS
-- Purpose: Store parking session transactions
-- ════════════════════════════════════════════════════════════════════════════════

CREATE TABLE transactions (
    transaction_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique transaction identifier',
    transaction_code VARCHAR(50) NOT NULL UNIQUE COMMENT 'Transaction code: TXN-YYYYMMDD-XXXXX',
    vehicle_id INT NOT NULL COMMENT 'Parked vehicle',
    spot_id INT COMMENT 'Assigned parking spot',
    entry_timestamp TIMESTAMP NOT NULL COMMENT 'Vehicle entry date/time',
    exit_timestamp TIMESTAMP COMMENT 'Vehicle exit date/time',
    parking_duration_minutes INT COMMENT 'Total parking duration in minutes',
    transaction_status VARCHAR(20) NOT NULL DEFAULT 'ACTIVE' COMMENT 'ACTIVE, COMPLETED, CANCELLED',
    entry_gate_id INT COMMENT 'Entry gate identifier',
    exit_gate_id INT COMMENT 'Exit gate identifier',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last update',
    
    CONSTRAINT fk_transactions_vehicle FOREIGN KEY (vehicle_id) REFERENCES vehicles(vehicle_id) ON DELETE CASCADE,
    CONSTRAINT fk_transactions_spot FOREIGN KEY (spot_id) REFERENCES parking_spots(spot_id) ON DELETE SET NULL,
    INDEX idx_transaction_code (transaction_code),
    INDEX idx_vehicle_id (vehicle_id),
    INDEX idx_transaction_status (transaction_status),
    INDEX idx_entry_timestamp (entry_timestamp),
    INDEX idx_exit_timestamp (exit_timestamp)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Parking session transactions with entry/exit tracking';

-- ════════════════════════════════════════════════════════════════════════════════
-- TABLE 6: PAYMENT_RECORDS
-- Purpose: Store payment information for parked vehicles
-- ════════════════════════════════════════════════════════════════════════════════

CREATE TABLE payment_records (
    payment_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique payment identifier',
    transaction_id INT NOT NULL COMMENT 'Associated transaction',
    parking_fee DECIMAL(10, 2) NOT NULL COMMENT 'Calculated parking fee',
    fee_calculation_method VARCHAR(50) COMMENT 'STANDARD, PROGRESSIVE, TIME_BASED',
    hourly_rate DECIMAL(10, 2) NOT NULL COMMENT 'Hourly rate applied',
    duration_hours DECIMAL(10, 2) NOT NULL COMMENT 'Parking duration in hours',
    discount_amount DECIMAL(10, 2) DEFAULT 0 COMMENT 'Applied discount',
    final_amount_due DECIMAL(10, 2) NOT NULL COMMENT 'Final amount payable',
    payment_status VARCHAR(20) NOT NULL DEFAULT 'PENDING' COMMENT 'PENDING, COMPLETED, FAILED, REFUNDED',
    payment_method VARCHAR(50) COMMENT 'CASH, CARD, UPI, WALLET, MONTHLY_PASS',
    payment_timestamp TIMESTAMP COMMENT 'Payment processing timestamp',
    transaction_reference VARCHAR(50) COMMENT 'Payment gateway reference',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last update',
    
    CONSTRAINT fk_payment_transaction FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id) ON DELETE CASCADE,
    INDEX idx_payment_status (payment_status),
    INDEX idx_transaction_id (transaction_id),
    INDEX idx_payment_timestamp (payment_timestamp)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Payment records with fee calculations and processing status';

-- ════════════════════════════════════════════════════════════════════════════════
-- TABLE 7: PRICING_RULES
-- Purpose: Store configurable pricing rules
-- ════════════════════════════════════════════════════════════════════════════════

CREATE TABLE pricing_rules (
    rule_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique pricing rule identifier',
    vehicle_type_id INT NOT NULL COMMENT 'Vehicle type for this rule',
    pricing_strategy VARCHAR(50) NOT NULL COMMENT 'STANDARD, PROGRESSIVE, TIME_BASED',
    hourly_rate DECIMAL(10, 2) NOT NULL COMMENT 'Base hourly rate',
    min_charge DECIMAL(10, 2) DEFAULT 0 COMMENT 'Minimum charge amount',
    max_daily_charge DECIMAL(10, 2) COMMENT 'Maximum charge per day',
    night_multiplier DECIMAL(5, 2) DEFAULT 1.0 COMMENT 'Rate multiplier for night hours',
    peak_multiplier DECIMAL(5, 2) DEFAULT 1.0 COMMENT 'Rate multiplier for peak hours',
    peak_start_hour INT DEFAULT 9 COMMENT 'Peak hours start (0-23)',
    peak_end_hour INT DEFAULT 18 COMMENT 'Peak hours end (0-23)',
    active BOOLEAN DEFAULT TRUE COMMENT 'Whether rule is active',
    effective_from DATE NOT NULL COMMENT 'Rule effective date',
    effective_to DATE COMMENT 'Rule end date (null = ongoing)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Rule creation date',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last update',
    
    CONSTRAINT fk_pricing_vehicle_type FOREIGN KEY (vehicle_type_id) REFERENCES vehicle_types(vehicle_type_id) ON DELETE CASCADE,
    INDEX idx_vehicle_type (vehicle_type_id),
    INDEX idx_pricing_strategy (pricing_strategy),
    INDEX idx_effective_from (effective_from)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Configurable pricing rules for different strategies';

-- ════════════════════════════════════════════════════════════════════════════════
-- TABLE 8: ALLOCATION_HISTORY
-- Purpose: Track spot allocation decisions for auditing and optimization
-- ════════════════════════════════════════════════════════════════════════════════

CREATE TABLE allocation_history (
    allocation_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique allocation record',
    transaction_id INT NOT NULL COMMENT 'Associated transaction',
    allocation_strategy VARCHAR(50) NOT NULL COMMENT 'LOAD_BALANCED, SIZE_OPTIMIZED, NEAREST_TO_EXIT',
    requested_spot_size VARCHAR(20) NOT NULL COMMENT 'Requested spot size',
    allocated_spot_id INT NOT NULL COMMENT 'Actually allocated spot',
    allocation_score DECIMAL(10, 2) COMMENT 'Algorithm score/priority',
    alternatives_count INT DEFAULT 0 COMMENT 'Number of available alternatives',
    allocation_time_ms INT COMMENT 'Time taken for allocation in milliseconds',
    success BOOLEAN DEFAULT TRUE COMMENT 'Whether allocation was successful',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Allocation timestamp',
    
    CONSTRAINT fk_allocation_transaction FOREIGN KEY (transaction_id) REFERENCES transactions(transaction_id) ON DELETE CASCADE,
    CONSTRAINT fk_allocation_spot FOREIGN KEY (allocated_spot_id) REFERENCES parking_spots(spot_id) ON DELETE CASCADE,
    INDEX idx_allocation_strategy (allocation_strategy),
    INDEX idx_transaction_id (transaction_id),
    INDEX idx_created_at (created_at)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Spot allocation history for auditing and optimization analysis';

-- ════════════════════════════════════════════════════════════════════════════════
-- TABLE 9: USERS (Parking Lot Operators/Admins)
-- Purpose: Store parking lot staff and operator information
-- ════════════════════════════════════════════════════════════════════════════════

CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique user identifier',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT 'Login username',
    email VARCHAR(100) NOT NULL UNIQUE COMMENT 'User email address',
    full_name VARCHAR(100) NOT NULL COMMENT 'Full name',
    user_role VARCHAR(50) NOT NULL COMMENT 'ADMIN, OPERATOR, MANAGER, VIEWER',
    password_hash VARCHAR(255) NOT NULL COMMENT 'Hashed password (bcrypt)',
    phone VARCHAR(20) COMMENT 'Contact phone number',
    active BOOLEAN DEFAULT TRUE COMMENT 'User account active status',
    last_login TIMESTAMP COMMENT 'Last login timestamp',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Account creation date',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last update',
    
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_user_role (user_role)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Parking lot staff and operator user accounts';

-- ════════════════════════════════════════════════════════════════════════════════
-- TABLE 10: AUDIT_LOGS
-- Purpose: Track all system activities for compliance and debugging
-- ════════════════════════════════════════════════════════════════════════════════

CREATE TABLE audit_logs (
    log_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique log entry identifier',
    user_id INT COMMENT 'User who performed the action',
    action_type VARCHAR(100) NOT NULL COMMENT 'Type of action: ENTRY, EXIT, PAYMENT, CONFIG_CHANGE',
    entity_type VARCHAR(50) NOT NULL COMMENT 'Entity affected: VEHICLE, TRANSACTION, PAYMENT, SPOT',
    entity_id INT NOT NULL COMMENT 'ID of affected entity',
    details JSON COMMENT 'Additional action details in JSON format',
    status VARCHAR(20) NOT NULL COMMENT 'SUCCESS, FAILURE, PARTIAL',
    error_message TEXT COMMENT 'Error message if action failed',
    ip_address VARCHAR(45) COMMENT 'Client IP address',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Log entry timestamp',
    
    CONSTRAINT fk_audit_user FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_action_type (action_type),
    INDEX idx_entity_type (entity_type),
    INDEX idx_created_at (created_at),
    INDEX idx_user_id (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Audit logs for compliance, debugging, and activity tracking';

-- ════════════════════════════════════════════════════════════════════════════════
-- TABLE 11: PARKING_LOT_STATS
-- Purpose: Store daily/hourly parking lot statistics for analytics
-- ════════════════════════════════════════════════════════════════════════════════

CREATE TABLE parking_lot_stats (
    stat_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique stat record',
    stat_date DATE NOT NULL COMMENT 'Date of statistics',
    stat_hour INT COMMENT 'Hour of day (0-23), null for daily stats',
    total_vehicles_entered INT DEFAULT 0 COMMENT 'Total vehicles entered',
    total_vehicles_exited INT DEFAULT 0 COMMENT 'Total vehicles exited',
    peak_occupancy INT DEFAULT 0 COMMENT 'Maximum occupancy during period',
    average_occupancy INT DEFAULT 0 COMMENT 'Average occupancy during period',
    total_revenue DECIMAL(15, 2) DEFAULT 0 COMMENT 'Total revenue for period',
    average_parking_duration_minutes INT DEFAULT 0 COMMENT 'Average parking duration',
    failed_entries INT DEFAULT 0 COMMENT 'Number of failed entry attempts',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Record creation',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last update',
    
    UNIQUE KEY unique_daily_stat (stat_date, stat_hour),
    INDEX idx_stat_date (stat_date),
    INDEX idx_stat_hour (stat_hour)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='Daily and hourly parking lot statistics for analytics and reporting';

-- ════════════════════════════════════════════════════════════════════════════════
-- TABLE 12: CONFIGURATION
-- Purpose: Store system configuration parameters
-- ════════════════════════════════════════════════════════════════════════════════

CREATE TABLE configuration (
    config_id INT PRIMARY KEY AUTO_INCREMENT COMMENT 'Unique configuration parameter',
    config_key VARCHAR(100) NOT NULL UNIQUE COMMENT 'Configuration key',
    config_value VARCHAR(500) NOT NULL COMMENT 'Configuration value',
    config_type VARCHAR(50) COMMENT 'Type: STRING, INT, BOOLEAN, DECIMAL',
    description TEXT COMMENT 'Parameter description',
    is_modifiable BOOLEAN DEFAULT TRUE COMMENT 'Can this parameter be modified',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT 'Parameter creation date',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT 'Last modification date',
    
    INDEX idx_config_key (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci
COMMENT='System configuration parameters';

-- ════════════════════════════════════════════════════════════════════════════════
-- CREATE INDEXES
-- ════════════════════════════════════════════════════════════════════════════════

-- Composite indexes for common queries
CREATE INDEX idx_transaction_vehicle_status ON transactions(vehicle_id, transaction_status);
CREATE INDEX idx_spot_floor_status ON parking_spots(floor_id, spot_status);
CREATE INDEX idx_payment_transaction_status ON payment_records(transaction_id, payment_status);

-- ════════════════════════════════════════════════════════════════════════════════
-- DATABASE SCHEMA COMPLETE
-- ════════════════════════════════════════════════════════════════════════════════
