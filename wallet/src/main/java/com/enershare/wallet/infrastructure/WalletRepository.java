package com.enershare.wallet.infrastructure;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enershare.wallet.domain.Wallet;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, UUID>{
    // A user might not have a wallet yet so "Optional" is used 
    Optional<Wallet> findByHouseholdId(UUID householdId); 
}
