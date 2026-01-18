package com.enershare.trading.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "trades")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trade {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID buyerId;
    private UUID sellerId;

    private double kwhAmount;
    private double pricePerKwh;
    private double totalPrice;

    private Instant timestamp;
}
