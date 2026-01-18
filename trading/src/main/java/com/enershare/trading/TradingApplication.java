package com.enershare.trading;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

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
// Scanning all comlponents (Services, Controllers) in Trading
@ComponentScan(basePackages = "com.enershare.trading")
// we specify where are JPA repository (Postgres) -> Trading
@EnableJpaRepositories(basePackages = "com.enershare.trading.infrastructure")
// we specify where are JPA entities(Postgres) -> Trading
@EntityScan(basePackages = "com.enershare.trading.domain")

public class TradingApplication {
    public static void main(String [] args){
        SpringApplication.run(TradingApplication.class, args);
    }
   
}
