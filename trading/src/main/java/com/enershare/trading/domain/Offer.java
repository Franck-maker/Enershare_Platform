package com.enershare.trading.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.Instant;
import java.util.*; 

@Entity
@Table (name="offers")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    //Id of the household who is selling
    @Column(nullable = false)
    private UUID sellerId; 

    //how much energy
    @Column(nullable = false)
    private Double kwhAmount;

    // At what price per kwh
    @Column(nullable = false)
    private Double pricePerKwh;

    @Builder.Default //Ensure Builder uses this default value
    @Column(nullable = false)
    private Instant createdAt = Instant.now(); 

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OfferStatus status;


    public enum OfferStatus{
        OPEN, MATCHED, CANCELLED
    }

    
}
