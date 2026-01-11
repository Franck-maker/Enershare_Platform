package com.enershare.wallet.application;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.enershare.common.events.TradeMatchedEvent;
import com.enershare.wallet.domain.Wallet;
import com.enershare.wallet.infrastructure.WalletRepository;

import jakarta.transaction.Transactional;
import lombok.*;

@Component
@RequiredArgsConstructor
@Service
public class WalletEventListener {
    private final WalletRepository walletRepository;
    
    /**
     * This method is automatically triggered whenever a TradeMatchedEvent 
     * is published anywhere in the application (e.g., by the Trading Engine).
     */

    @EventListener 
    @Transactional // CRITICAL: Ensures the debit and credit happen together. If one fails, everything rolls back.
    public void onTradeMatched(TradeMatchedEvent event){

        System.out.println("💰 Wallet Service: Processing payment for Trade " + event.getTradeId());

        //Find the Buyer's Wallet
        Wallet buyerWallet = walletRepository.findByHouseholdId(event.getBuyerId())
       .orElseGet(() -> walletRepository.save(Wallet.builder()
                        .householdId(event.getBuyerId())
                        .balance(0.0)
                        .build()));

        //Find the Seller's Wallet
        Wallet sellerWallet = walletRepository.findByHouseholdId(event.getSellerId())
        .orElseGet(() -> walletRepository.save(Wallet.builder()
                        .householdId(event.getSellerId())
                        .balance(0.0)
                        .build()));

        //Rule Check: Insufficient Funds
        if(buyerWallet.getBalance() < event.getTotalPrice()){
            System.err.println("❌ PAYMENT FAILED: User " + event.getBuyerId() + " has insufficient funds.");
            //throwing exception will rollback the transaction (thanks to @Transactional)
            throw new RuntimeException("Insufficient Funds - Transaction Rolled Back");
        }

        // Execute Transfer
        buyerWallet.withdrawFunds(event.getTotalPrice());
        sellerWallet.addFunds(event.getTotalPrice());

        //Save Changes
        walletRepository.save(buyerWallet);
        walletRepository.save(sellerWallet);

        System.out.println("✅ Payment Successful: " + event.getTotalPrice() + "€ transferred from " 
                + event.getBuyerId() + " to " + event.getSellerId());
        

    }
    
}
