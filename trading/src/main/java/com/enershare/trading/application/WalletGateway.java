package com.enershare.trading.application;

import java.util.UUID;

/**
 * Interface (Port) for Wallet operations.
 */
public interface WalletGateway {
    /**
     * Reserves/Freezes funds for a bid.
     * @return true if funds were successfully reserved
     */
    boolean reserveFunds(UUID buyerId, double amount);
    
    /**
     * Transfers money from buyer to seller.
     */
    void transferFunds(UUID buyerId, UUID sellerId, double amount);
}
