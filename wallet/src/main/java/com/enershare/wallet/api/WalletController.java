package com.enershare.wallet.api;

import com.enershare.wallet.domain.Wallet;
import com.enershare.wallet.infrastructure.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RestController
@RequestMapping("/api/wallets")
@RequiredArgsConstructor
public class WalletController {

    private final WalletRepository walletRepository;

    // POST /wallet/{id}/addFunds
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

    //Reserve funds (Locking logic simulation)
    // POST /api/wallets/{id}/reserve?amount=X
    @PostMapping("/{householdId}/reserve")
    public boolean reserveFunds(@PathVariable UUID householdId, @RequestParam Double amount) {
        return walletRepository.findByHouseholdId(householdId)
                .map(wallet -> wallet.getBalance() >= amount) 
                .orElse(false);
    }

    // Transfer funds
    // POST /api/transactions
    @PostMapping("/transfer")
    @Transactional
    public void transfer(@RequestBody TransferRequest request) {
        Wallet buyerWallet = walletRepository.findByHouseholdId(request.from())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Buyer Wallet not found"));
        
        Wallet sellerWallet = walletRepository.findByHouseholdId(request.to())
                 .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Seller Wallet not found"));

        if (buyerWallet.getBalance() < request.amount()) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient funds");
        }

        buyerWallet.withdrawFunds(request.amount());
        sellerWallet.addFunds(request.amount());

        walletRepository.save(buyerWallet);
        walletRepository.save(sellerWallet);
    }

    public record TransferRequest(UUID from, UUID to, Double amount) {}


    // POST /wallet/{id}/withdraw
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