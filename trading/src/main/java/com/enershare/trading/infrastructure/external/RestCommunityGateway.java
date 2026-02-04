package com.enershare.trading.infrastructure.external;

import com.enershare.trading.application.CommunityGateway;
import lombok.RequiredArgsConstructor;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class RestCommunityGateway implements CommunityGateway {

    private final RestTemplate restTemplate;

    @Value("${services.community.url}")
    private String communityUrl;

    @Override
    public boolean isProsumer(UUID householdId) {
        try {
            // e.g. http://enershare-community:8081/api/households/{id}
            String url = communityUrl + "/api/households/" + householdId.toString();
            HouseholdDto dto = restTemplate.getForObject(url, HouseholdDto.class);
            
            // Allow if role is "PROSUMER". 
            if (dto != null && "PROSUMER".equalsIgnoreCase(dto.getRole())) {
                return true;
            }
            return false;
        } catch (Exception e) {
            System.err.println("Community check failed (Role validation): " + e.getMessage());
            // If we can't verify, we should probably fail safe (false).
            return false; 
        }
    }

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    static class HouseholdDto {
        private String role;
        private String firstName;
        private String lastName;
    }
}
