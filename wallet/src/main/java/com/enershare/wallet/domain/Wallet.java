package com.enershare.wallet.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*; 

@Data
@Entity
@Table(name="wallets")
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Wallet {
    @Id
    @GeneratedValue(strategy= GenerationType.UUID)
    private UUID id; 

    @Column(nullable = false, unique = true) // A househol has only one wallet
    private UUID householdId; 

    @Column(nullable = false)
    @Builder.Default //Ensure Builder uses this default value
    private Double balance = 0.0; 

    public void addFunds(Double amount){
        if(amount > 0){
        this.balance += amount; 
        }
    }

    public void withdrawFunds(Double amount){
        this.balance -= amount; 
    }
    
}
