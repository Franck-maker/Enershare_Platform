package com.enershare.community.application;


import org.springframework.stereotype.Service;
import com.enershare.community.domain.*;

import com.enershare.community.infrastructure.HouseholdRepository;

import lombok.*; 

@Service
@RequiredArgsConstructor //Generates a constructor for final fields
public class HouseholdApplicationService {
    
    //Dependency injection
    // we make this 'final' so Lombok injects it automatically
    private final HouseholdRepository householdRepository;

    public Household register(CreateHouseholdCommand command){
        Household.HouseholdRole role = Household.HouseholdRole.CONSUMER;
        if (command.getRole() != null) {
            try {
                role = Household.HouseholdRole.valueOf(command.getRole().toUpperCase());
            } catch (IllegalArgumentException e) {
                // Default to CONSUMER or throw error
                // For now, keep CONSUMER
            }
        }

        Household household = Household.builder()
        .firstName(command.getFirstName())
        .lastName(command.getLastName())
        .address(command.getAddress())
        .status(Household.HouseholdStatus.ACTIVE)
        .role(role)
        .build();
        return householdRepository.save(household); 
    }

    public Household getHousehold(java.util.UUID id) {
        return householdRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Household not found"));
    }


}
