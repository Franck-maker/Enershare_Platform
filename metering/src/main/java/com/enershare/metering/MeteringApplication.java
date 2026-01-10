package com.enershare.metering;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h2>Metering Context (Bounded Context)</h2>
 * <p>
 * This service handles the high-volume ingestion of energy data (IoT).
 * </p>
 * <ul>
 * <li><b>Responsibility:</b> Receives real-time consumption/production streams from Smart Meters.</li>
 * <li><b>Database:</b> MongoDB (Optimized for Write-Heavy Time-Series data).</li>
 * <li><b>Communication:</b> Provides aggregated data to the Trading engine for validation.</li>
 * </ul>
 */
@SpringBootApplication
public class MeteringApplication {
    public static void main(String[] args) {
        SpringApplication.run(MeteringApplication.class, args);
    }
}