package com.enershare.community.domain;

import java.util.*;
import jakarta.persistence.*;
import lombok.*; 

@Entity
@Table(name = "household")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder //Allows : Househol

public class Household {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName; 
    private String address; 

    @Enumerated(EnumType.STRING)
    @Column(nullable= false)
    private HouseholdStatus status;

    @Enumerated(EnumType.STRING)
    @Column(nullable= false)
    private HouseholdRole role;

    public enum HouseholdStatus {
        ACTIVE, INACTIVE, PENDING_VALIDATION
    }

    public enum HouseholdRole {
        CONSUMER, PROSUMER
    }   
    
}
