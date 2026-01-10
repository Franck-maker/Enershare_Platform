package com.enershare.trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h2>Trading Context (Bounded Context)</h2>
 * <p>
 * This is the <b>Core Domain</b> of the EnerShare platform. It manages the marketplace logic.
 * </p>
 * <ul>
 * <li><b>Responsibility:</b> Manages Trading Sessions, matches Bids (Buy) and Offers (Sell).</li>
 * <li><b>Database:</b> PostgreSQL (Transactional integrity required for contracts).</li>
 * <li><b>Communication:</b> Orchestrates the flow: Match -> Payment -> Finalization.</li>
 * </ul>
 */
@SpringBootApplication

public class TradingApplication {
    public static void main(String [] args){
        SpringApplication.run(TradingApplication.class, args);
    }
   
}
