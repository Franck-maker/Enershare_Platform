package com.enershare.trading.domain;

import jakarta.persistence.*; 
import lombok.*;

import java.time.Instant;
import java.util.*; 

@Entity
@Table (name="bids")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class Bid {
     @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    //Id of the household who is trying to buy energy
    @Column(nullable = false)
    private UUID buyerId; 

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
    private BidStatus status;


    public enum BidStatus{
        OPEN, MATCHED, CANCELLED
    }
    
}
