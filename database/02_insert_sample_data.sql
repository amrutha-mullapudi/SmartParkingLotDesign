-- ════════════════════════════════════════════════════════════════════════════════
-- SMART PARKING LOT SYSTEM - SAMPLE DATA
-- This script initializes the database with sample data for testing
-- ════════════════════════════════════════════════════════════════════════════════

USE smart_parking_lot;

-- ════════════════════════════════════════════════════════════════════════════════
-- INSERT VEHICLE TYPES
-- ════════════════════════════════════════════════════════════════════════════════

INSERT INTO vehicle_types (type_name, size_category, weight_kg, base_hourly_rate, spots_required, active)
VALUES 
    ('Bike', 'SMALL', 150, 1.50, 1, TRUE),
    ('Car', 'MEDIUM', 1500, 3.00, 1, TRUE),
    ('Bus', 'LARGE', 12000, 8.00, 1, TRUE),
    ('Truck', 'EXTRA_LARGE', 8000, 6.00, 2, TRUE);

-- ════════════════════════════════════════════════════════════════════════════════
-- INSERT FLOORS
-- ════════════════════════════════════════════════════════════════════════════════

INSERT INTO floors (floor_number, total_spots, available_spots, active)
VALUES 
    (0, 20, 20, TRUE),
    (1, 20, 20, TRUE),
    (2, 20, 20, TRUE);

-- ════════════════════════════════════════════════════════════════════════════════
-- INSERT PARKING SPOTS
-- ════════════════════════════════════════════════════════════════════════════════

-- Floor 0: Spots F0-S0 to F0-S19
INSERT INTO parking_spots (spot_identifier, floor_id, spot_size, spot_status)
VALUES 
    ('F0-S0', 1, 'SMALL', 'AVAILABLE'),
    ('F0-S1', 1, 'SMALL', 'AVAILABLE'),
    ('F0-S2', 1, 'SMALL', 'AVAILABLE'),
    ('F0-S3', 1, 'SMALL', 'AVAILABLE'),
    ('F0-S4', 1, 'MEDIUM', 'AVAILABLE'),
    ('F0-S5', 1, 'MEDIUM', 'AVAILABLE'),
    ('F0-S6', 1, 'MEDIUM', 'AVAILABLE'),
    ('F0-S7', 1, 'MEDIUM', 'AVAILABLE'),
    ('F0-S8', 1, 'LARGE', 'AVAILABLE'),
    ('F0-S9', 1, 'LARGE', 'AVAILABLE'),
    ('F0-S10', 1, 'LARGE', 'AVAILABLE'),
    ('F0-S11', 1, 'LARGE', 'AVAILABLE'),
    ('F0-S12', 1, 'EXTRA_LARGE', 'AVAILABLE'),
    ('F0-S13', 1, 'EXTRA_LARGE', 'AVAILABLE'),
    ('F0-S14', 1, 'EXTRA_LARGE', 'AVAILABLE'),
    ('F0-S15', 1, 'EXTRA_LARGE', 'AVAILABLE'),
    ('F0-S16', 1, 'SMALL', 'AVAILABLE'),
    ('F0-S17', 1, 'SMALL', 'AVAILABLE'),
    ('F0-S18', 1, 'MEDIUM', 'AVAILABLE'),
    ('F0-S19', 1, 'MEDIUM', 'AVAILABLE');

-- Floor 1: Spots F1-S0 to F1-S19
INSERT INTO parking_spots (spot_identifier, floor_id, spot_size, spot_status)
VALUES 
    ('F1-S0', 2, 'SMALL', 'AVAILABLE'),
    ('F1-S1', 2, 'SMALL', 'AVAILABLE'),
    ('F1-S2', 2, 'SMALL', 'AVAILABLE'),
    ('F1-S3', 2, 'SMALL', 'AVAILABLE'),
    ('F1-S4', 2, 'MEDIUM', 'AVAILABLE'),
    ('F1-S5', 2, 'MEDIUM', 'AVAILABLE'),
    ('F1-S6', 2, 'MEDIUM', 'AVAILABLE'),
    ('F1-S7', 2, 'MEDIUM', 'AVAILABLE'),
    ('F1-S8', 2, 'LARGE', 'AVAILABLE'),
    ('F1-S9', 2, 'LARGE', 'AVAILABLE'),
    ('F1-S10', 2, 'LARGE', 'AVAILABLE'),
    ('F1-S11', 2, 'LARGE', 'AVAILABLE'),
    ('F1-S12', 2, 'EXTRA_LARGE', 'AVAILABLE'),
    ('F1-S13', 2, 'EXTRA_LARGE', 'AVAILABLE'),
    ('F1-S14', 2, 'EXTRA_LARGE', 'AVAILABLE'),
    ('F1-S15', 2, 'EXTRA_LARGE', 'AVAILABLE'),
    ('F1-S16', 2, 'SMALL', 'AVAILABLE'),
    ('F1-S17', 2, 'SMALL', 'AVAILABLE'),
    ('F1-S18', 2, 'MEDIUM', 'AVAILABLE'),
    ('F1-S19', 2, 'MEDIUM', 'AVAILABLE');

-- Floor 2: Spots F2-S0 to F2-S19
INSERT INTO parking_spots (spot_identifier, floor_id, spot_size, spot_status)
VALUES 
    ('F2-S0', 3, 'SMALL', 'AVAILABLE'),
    ('F2-S1', 3, 'SMALL', 'AVAILABLE'),
    ('F2-S2', 3, 'SMALL', 'AVAILABLE'),
    ('F2-S3', 3, 'SMALL', 'AVAILABLE'),
    ('F2-S4', 3, 'MEDIUM', 'AVAILABLE'),
    ('F2-S5', 3, 'MEDIUM', 'AVAILABLE'),
    ('F2-S6', 3, 'MEDIUM', 'AVAILABLE'),
    ('F2-S7', 3, 'MEDIUM', 'AVAILABLE'),
    ('F2-S8', 3, 'LARGE', 'AVAILABLE'),
    ('F2-S9', 3, 'LARGE', 'AVAILABLE'),
    ('F2-S10', 3, 'LARGE', 'AVAILABLE'),
    ('F2-S11', 3, 'LARGE', 'AVAILABLE'),
    ('F2-S12', 3, 'EXTRA_LARGE', 'AVAILABLE'),
    ('F2-S13', 3, 'EXTRA_LARGE', 'AVAILABLE'),
    ('F2-S14', 3, 'EXTRA_LARGE', 'AVAILABLE'),
    ('F2-S15', 3, 'EXTRA_LARGE', 'AVAILABLE'),
    ('F2-S16', 3, 'SMALL', 'AVAILABLE'),
    ('F2-S17', 3, 'SMALL', 'AVAILABLE'),
    ('F2-S18', 3, 'MEDIUM', 'AVAILABLE'),
    ('F2-S19', 3, 'MEDIUM', 'AVAILABLE');

-- ════════════════════════════════════════════════════════════════════════════════
-- INSERT SAMPLE VEHICLES
-- ════════════════════════════════════════════════════════════════════════════════

INSERT INTO vehicles (license_plate, vehicle_type_id, owner_name, owner_contact, owner_email, active)
VALUES 
    ('KA-01-AB-1001', 2, 'Rajesh Kumar', '9876543210', 'rajesh@example.com', TRUE),
    ('KA-01-AB-1002', 2, 'Priya Singh', '9876543211', 'priya@example.com', TRUE),
    ('KA-01-AB-1003', 2, 'Amit Patel', '9876543212', 'amit@example.com', TRUE),
    ('KA-01-AB-1004', 2, 'Sneha Desai', '9876543213', 'sneha@example.com', TRUE),
    ('KA-01-AB-2001', 1, 'Rohan Sharma', '9876543214', 'rohan@example.com', TRUE),
    ('KA-01-AB-2002', 1, 'Anjali Verma', '9876543215', 'anjali@example.com', TRUE),
    ('KA-01-AB-3001', 3, 'Transport Co', '9876543216', 'bus@company.com', TRUE),
    ('KA-01-AB-4001', 4, 'Logistics Inc', '9876543217', 'truck@company.com', TRUE),
    ('KA-01-AB-1005', 2, 'Vikram Nair', '9876543218', 'vikram@example.com', TRUE),
    ('KA-01-AB-1006', 2, 'Neha Kapoor', '9876543219', 'neha@example.com', TRUE);

-- ════════════════════════════════════════════════════════════════════════════════
-- INSERT PRICING RULES
-- ════════════════════════════════════════════════════════════════════════════════

INSERT INTO pricing_rules (vehicle_type_id, pricing_strategy, hourly_rate, min_charge, max_daily_charge, 
                           night_multiplier, peak_multiplier, peak_start_hour, peak_end_hour, 
                           active, effective_from)
VALUES 
    -- Bike pricing (Standard)
    (1, 'STANDARD', 1.50, 1.00, 20.00, 0.75, 1.25, 9, 18, TRUE, CURDATE()),
    
    -- Car pricing (Progressive)
    (2, 'PROGRESSIVE', 3.00, 2.00, 50.00, 0.80, 1.50, 9, 18, TRUE, CURDATE()),
    
    -- Bus pricing (Time-Based)
    (3, 'TIME_BASED', 8.00, 5.00, 100.00, 0.90, 1.40, 9, 18, TRUE, CURDATE()),
    
    -- Truck pricing (Standard)
    (4, 'STANDARD', 6.00, 5.00, 80.00, 0.85, 1.30, 9, 18, TRUE, CURDATE());

-- ════════════════════════════════════════════════════════════════════════════════
-- INSERT SAMPLE USERS
-- ════════════════════════════════════════════════════════════════════════════════

INSERT INTO users (username, email, full_name, user_role, password_hash, phone, active)
VALUES 
    ('admin', 'admin@parking.com', 'System Administrator', 'ADMIN', 
     '$2b$10$abcdefghijklmnopqrstuvwxyz1234567890', '9000000001', TRUE),
    ('operator1', 'operator1@parking.com', 'Gate Operator 1', 'OPERATOR', 
     '$2b$10$abcdefghijklmnopqrstuvwxyz1234567890', '9000000002', TRUE),
    ('operator2', 'operator2@parking.com', 'Gate Operator 2', 'OPERATOR', 
     '$2b$10$abcdefghijklmnopqrstuvwxyz1234567890', '9000000003', TRUE),
    ('manager', 'manager@parking.com', 'Parking Manager', 'MANAGER', 
     '$2b$10$abcdefghijklmnopqrstuvwxyz1234567890', '9000000004', TRUE),
    ('viewer', 'viewer@parking.com', 'Analytics Viewer', 'VIEWER', 
     '$2b$10$abcdefghijklmnopqrstuvwxyz1234567890', '9000000005', TRUE);

-- ════════════════════════════════════════════════════════════════════════════════
-- INSERT CONFIGURATION PARAMETERS
-- ════════════════════════════════════════════════════════════════════════════════

INSERT INTO configuration (config_key, config_value, config_type, description, is_modifiable)
VALUES 
    ('TOTAL_FLOORS', '3', 'INT', 'Total number of parking floors', FALSE),
    ('SPOTS_PER_FLOOR', '20', 'INT', 'Parking spots per floor', FALSE),
    ('ALLOCATION_STRATEGY', 'LOAD_BALANCED', 'STRING', 'Default spot allocation strategy', TRUE),
    ('FEE_CALCULATION_STRATEGY', 'PROGRESSIVE', 'STRING', 'Default fee calculation strategy', TRUE),
    ('GRACE_PERIOD_MINUTES', '15', 'INT', 'Grace period before charging starts', TRUE),
    ('MAX_DAILY_RATE_CAR', '50.00', 'DECIMAL', 'Maximum daily parking fee for cars', TRUE),
    ('NIGHT_HOURS_START', '22', 'INT', 'Night rate start hour', TRUE),
    ('NIGHT_HOURS_END', '6', 'INT', 'Night rate end hour', TRUE),
    ('ENABLE_MONTHLY_PASS', 'true', 'BOOLEAN', 'Enable monthly pass option', TRUE),
    ('SYSTEM_ACTIVE', 'true', 'BOOLEAN', 'System operational status', TRUE),
    ('MAINTENANCE_MODE', 'false', 'BOOLEAN', 'System maintenance mode', TRUE);

-- ════════════════════════════════════════════════════════════════════════════════
-- INSERT SAMPLE TRANSACTION (for testing - optional)
-- ════════════════════════════════════════════════════════════════════════════════

-- Sample completed transaction
INSERT INTO transactions (transaction_code, vehicle_id, spot_id, entry_timestamp, exit_timestamp, 
                          parking_duration_minutes, transaction_status)
VALUES 
    ('TXN-20250101-00001', 1, 5, '2025-01-01 10:00:00', '2025-01-01 12:30:00', 150, 'COMPLETED');

-- Sample payment record for the above transaction
INSERT INTO payment_records (transaction_id, parking_fee, fee_calculation_method, hourly_rate, 
                             duration_hours, discount_amount, final_amount_due, payment_status, 
                             payment_method, payment_timestamp, transaction_reference)
VALUES 
    (1, 9.50, 'PROGRESSIVE', 3.00, 2.5, 0.50, 9.00, 'COMPLETED', 'CARD', 
     '2025-01-01 12:31:00', 'PAY-123456789');

-- Sample allocation history
INSERT INTO allocation_history (transaction_id, allocation_strategy, requested_spot_size, 
                                allocated_spot_id, allocation_score, alternatives_count, 
                                allocation_time_ms, success)
VALUES 
    (1, 'LOAD_BALANCED', 'MEDIUM', 5, 8.75, 3, 45, TRUE);

-- ════════════════════════════════════════════════════════════════════════════════
-- INSERT INITIAL STATISTICS (optional)
-- ════════════════════════════════════════════════════════════════════════════════

INSERT INTO parking_lot_stats (stat_date, stat_hour, total_vehicles_entered, total_vehicles_exited, 
                              peak_occupancy, average_occupancy, total_revenue, 
                              average_parking_duration_minutes, failed_entries)
VALUES 
    -- Daily stats for today
    (CURDATE(), NULL, 45, 44, 32, 18, 450.00, 165, 2),
    
    -- Hourly stats for different hours today
    (CURDATE(), 9, 12, 8, 10, 6, 45.00, 90, 0),
    (CURDATE(), 10, 8, 5, 13, 9, 54.00, 120, 1),
    (CURDATE(), 11, 10, 9, 15, 11, 65.00, 150, 0),
    (CURDATE(), 14, 15, 22, 8, 4, 120.00, 180, 1);

-- ════════════════════════════════════════════════════════════════════════════════
-- INSERT AUDIT LOG SAMPLE
-- ════════════════════════════════════════════════════════════════════════════════

INSERT INTO audit_logs (user_id, action_type, entity_type, entity_id, details, status)
VALUES 
    (1, 'SYSTEM_START', 'SYSTEM', 0, 
     JSON_OBJECT('timestamp', NOW(), 'module', 'ParkingLotManager'), 'SUCCESS'),
    (2, 'ENTRY', 'VEHICLE', 1, 
     JSON_OBJECT('license_plate', 'KA-01-AB-1001', 'spot', 'F0-S5', 'gate', 1), 'SUCCESS'),
    (2, 'EXIT', 'VEHICLE', 1, 
     JSON_OBJECT('license_plate', 'KA-01-AB-1001', 'spot', 'F0-S5', 'gate', 1, 'duration_min', 150), 'SUCCESS'),
    (3, 'PAYMENT', 'PAYMENT', 1, 
     JSON_OBJECT('amount', 9.00, 'method', 'CARD', 'reference', 'PAY-123456789'), 'SUCCESS'),
    (1, 'CONFIG_CHANGE', 'CONFIGURATION', 3, 
     JSON_OBJECT('param', 'ALLOCATION_STRATEGY', 'old_value', 'SIZE_OPTIMIZED', 'new_value', 'LOAD_BALANCED'), 'SUCCESS');

-- ════════════════════════════════════════════════════════════════════════════════
-- DATA INITIALIZATION COMPLETE
-- ════════════════════════════════════════════════════════════════════════════════
