package com.enershare.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h2>Wallet Context (Bounded Context)</h2>
 * <p>
 * This service acts as the internal <b>Ledger</b> for the platform.
 * </p>
 * <ul>
 * <li><b>Responsibility:</b> Manages virtual funds, deposits, withdrawals, and trade settlements.</li>
 * <li><b>Database:</b> PostgreSQL (Strict ACID transactions are mandatory for money).</li>
 * <li><b>Communication:</b> Listens to 'TradeMatched' events to execute payments.</li>
 * </ul>
 */
@SpringBootApplication

public class WalletApplication {
    public static void main(String [] args){
        SpringApplication.run(WalletApplication.class, args); 
    }
    
}
