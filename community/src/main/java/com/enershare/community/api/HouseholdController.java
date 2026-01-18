package com.enershare.community.api;

import org.springframework.web.bind.annotation.RestController;

import com.enershare.community.application.CreateHouseholdCommand;
import com.enershare.community.application.HouseholdApplicationService;
import com.enershare.community.domain.Household;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.http.HttpStatus;

import lombok.*;

@RestController
@RequiredArgsConstructor //Generates a constructor for final fields
@RequestMapping("/api/households")
public class HouseholdController {
    private final HouseholdApplicationService householdApplicationService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Household createHousehold(@RequestBody CreateHouseholdCommand command){
        return householdApplicationService.register(command);
    }

    @org.springframework.web.bind.annotation.GetMapping("/{id}")
    public Household getHousehold(@org.springframework.web.bind.annotation.PathVariable java.util.UUID id) {
        return householdApplicationService.getHousehold(id);
    }
}
