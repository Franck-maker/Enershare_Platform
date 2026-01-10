package com.enershare.metering.domain;

import lombok.*; 
import org.springframework.data.annotation.Id;
import java.time.*;
import org.springframework.data.mongodb.core.mapping.Document; 

// Document for MongoDB, different from PostgreSQl where we use @Entity
@Document(collection = "readings")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

// A Smart Meter sends a reading every 15 minutes 
public class MeterReading {
    @Id
    private String id; 
    //The link to the device that sent the reading
    private String smartMeterId; 
    //The energy value in kwh
    private Double kwh; 
    //The timestamp of the reading
    private Instant timestamp; 
  
}
