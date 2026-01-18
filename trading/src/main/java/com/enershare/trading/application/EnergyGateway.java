package com.enershare.trading.application;

import java.util.UUID;

/**
 * Interface (Port) 
 * Defines what data we need from external systems, without saying HOW to get it.
 */
public interface EnergyGateway {
    /**
     * Checks if a household has produced enough energy to cover the amount they want to sell.
     * @param householdId the user ID
     * @param requiredAmountKwh the amount they want to sell
     * @return true if they have enough production history
     */
    boolean hasSufficientProduction(UUID householdId, double requiredAmountKwh);
}
