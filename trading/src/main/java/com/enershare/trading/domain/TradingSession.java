package com.enershare.trading.domain;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "trading_sessions")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * handle trading sessions 
 * for example session from 2 to 3 PM
*/
public class TradingSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Instant startTime;
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    private SessionStatus status; // OPEN, CLOSED

    public enum SessionStatus {
        OPEN, CLOSED
    }
}