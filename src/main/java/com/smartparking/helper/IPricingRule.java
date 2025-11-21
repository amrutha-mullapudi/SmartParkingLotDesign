package com.smartparking.helper;

/**
 * Interface for defining pricing rules in the parking lot system.
 * Allows configuration of custom pricing logic beyond the fee calculators.
 */
public interface IPricingRule {

    /**
     * Apply a pricing rule to a base fee.
     * @param baseFee the base parking fee
     * @param context pricing context (e.g., vehicle type, duration, time of day)
     * @return adjusted fee after applying rule
     */
    double applyRule(double baseFee, PricingContext context);

    /**
     * Get the name of this pricing rule.
     * @return rule name (e.g., "Senior Discount", "Weekend Surcharge")
     */
    String getRuleName();

    /**
     * Check if this rule is currently active.
     * @return true if rule should be applied, false otherwise
     */
    boolean isActive();

    /**
     * Get the priority of this rule (higher priority applied first).
     * @return priority integer
     */
    int getPriority();
}
