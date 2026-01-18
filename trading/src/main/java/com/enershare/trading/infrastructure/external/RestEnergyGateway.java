package com.enershare.trading.infrastructure.external;

import com.enershare.trading.application.EnergyGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Value;

import java.util.UUID;

/**
 * Adapter Implementation
 * Uses REST calls to talk to the Metering Microservice.
 */
@Component
@RequiredArgsConstructor
public class RestEnergyGateway implements EnergyGateway {

    private final RestTemplate restTemplate;

    @Value("${services.metering.url}")
    private String meteringUrl;

    @Override
    public boolean hasSufficientProduction(UUID householdId, double requiredAmountKwh) {
        // In a real scenario, we would call: GET /api/meter-readings/summary/{id}
        // For now, let's assume we have an endpoint that returns the total production
        try {
            // This is how we call the OTHER container
            // We use the service name 'http://metering:8082' or configured URL
            Double totalProduction = restTemplate.getForObject(
                meteringUrl + "/api/meter-readings/" + householdId + "/total-production", 
                Double.class
            );
            
            return totalProduction != null && totalProduction >= requiredAmountKwh;
        } catch (Exception e) {
            System.err.println("Failed to contact Metering Service: " + e.getMessage());
            // Fail safe: if we can't verify, we don't allow the trade
            return false;
        }
    }
}
