package com.enershare.wallet.api;

import com.enershare.wallet.domain.Wallet;
import com.enershare.wallet.infrastructure.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletRepository walletRepository;

    // Requirement: POST /wallet/{id}/addFunds
    @PostMapping("/{householdId}/fund")
    public Wallet addFunds(@PathVariable UUID householdId, @RequestParam Double amount) {
        Wallet wallet = walletRepository.findByHouseholdId(householdId)
                .orElseGet(() -> Wallet.builder()
                        .householdId(householdId)
                        .balance(0.0)
                        .build());
        
        wallet.addFunds(amount);
        return walletRepository.save(wallet);
    }

    // Requirement: POST /wallet/{id}/withdraw
    @PostMapping("/{householdId}/withdraw")
    public Wallet withdrawFunds(@PathVariable UUID householdId, @RequestParam Double amount) {
        Wallet wallet = walletRepository.findByHouseholdId(householdId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Wallet not found"));
        
        // Basic rule validation
        if (wallet.getBalance() < amount) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }

        wallet.withdrawFunds(amount);
        return walletRepository.save(wallet);
    }
    
    // GET /api/wallets/{id}
    @GetMapping("/{householdId}")
    public Wallet getWallet(@PathVariable UUID householdId) {
        return walletRepository.findByHouseholdId(householdId)
                .orElseGet(() -> {
                    // if the wallet doesn't exist, create it
                    Wallet newWallet = Wallet.builder()
                            .householdId(householdId)
                            .balance(0.0)
                            .build();
                    return walletRepository.save(newWallet);
                });
            }
}