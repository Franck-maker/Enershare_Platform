package com.enershare.trading.infrastructure.external;

import com.enershare.trading.application.WalletGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestWalletGateway implements WalletGateway {

    private final RestTemplate restTemplate;

    @Value("${services.wallet.url}")
    private String walletUrl;

    @Override
    public boolean reserveFunds(UUID buyerId, double amount) {
        // Call Wallet Service to check balance and lock funds
        // POST /api/wallets/{id}/reserve?amount=X
        try {
            String url = walletUrl + "/api/wallets/" + buyerId + "/reserve?amount=" + amount;
            // Returns Boolean.TRUE or Boolean.FALSE
            Boolean result = restTemplate.postForObject(url, null, Boolean.class);
            return result != null && result; 
        } catch (Exception e) {
            System.err.println("Wallet check failed: " + e.getMessage());
            return false;
        }
    }

    @Override
    public void transferFunds(UUID buyerId, UUID sellerId, double amount) {
        // Call Wallet Service to execute transfer
        // POST /api/wallets/transfer
        try {
            String url = walletUrl + "/api/wallets/transfer";
            var request = new TransferRequest(buyerId, sellerId, amount);
            restTemplate.postForObject(url, request, Void.class);
            System.out.println("💰 Fund Transfer Successful: " + amount + " from " + buyerId + " to " + sellerId);
        } catch (Exception e) {
            System.err.println("Transaction failed: " + e.getMessage());
        }
    }
    
    // DTO matching the one in WalletController
    public record TransferRequest(UUID from, UUID to, Double amount) {}
}
