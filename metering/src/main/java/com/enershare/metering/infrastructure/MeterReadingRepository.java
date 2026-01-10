package com.enershare.metering.infrastructure;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import com.enershare.metering.domain.*;
import java.util.*;

@Repository
public interface MeterReadingRepository extends MongoRepository<MeterReading, String> {

    // find all readings for a given smart meter
    List<MeterReading> findBySmartMeterId(String smartMeterId);

    
}
