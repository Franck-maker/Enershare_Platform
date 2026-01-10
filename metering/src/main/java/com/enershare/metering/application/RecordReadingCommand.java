package com.enershare.metering.application;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecordReadingCommand{

    private String smartMeterId;
    private Double kwh;
    
}