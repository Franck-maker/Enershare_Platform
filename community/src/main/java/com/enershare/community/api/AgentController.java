package com.enershare.community.api;

import com.enershare.community.domain.Household;
import com.enershare.community.infrastructure.HouseholdRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/agents")
@RequiredArgsConstructor
public class AgentController {

    private final HouseholdRepository householdRepository;

    //Freeze/unfreeze accounts
    @PatchMapping("/households/{id}/status")
    public Household changeStatus(@PathVariable UUID id, @RequestParam String status) {
        Household household = householdRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        try {
            // Convert String to Enum (ACTIVE, SUSPENDED)
            household.setStatus(Household.HouseholdStatus.valueOf(status.toUpperCase()));
        } catch (IllegalArgumentException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid Status");
        }

        return householdRepository.save(household);
    }
}