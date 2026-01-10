package com.enershare.community.infrastructure;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.*;
import com.enershare.community.domain.*;



@Repository

public interface HouseholdRepository extends JpaRepository<Household, UUID> {

}