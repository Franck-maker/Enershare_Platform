package com.enershare.community.application;

import lombok.*; 

@Data
@Builder
public class CreateHouseholdCommand {
    private String firstName;
    private String lastName;
    private String address;


}
