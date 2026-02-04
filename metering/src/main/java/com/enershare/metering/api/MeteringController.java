package com.enershare.metering.api; 

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import com.enershare.metering.application.*;


import lombok.*; 

@RestController
@RequiredArgsConstructor //Generates a constructor for final fields
@RequestMapping("/api/meter-readings")
public class MeteringController {

    private final MeteringApplicationService meteringApplicationService; 

    @PostMapping
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void recordReading(@RequestBody RecordReadingCommand command){
        meteringApplicationService.record(command);
    }

    @org.springframework.web.bind.annotation.GetMapping("/{smartMeterId}")
    public java.util.List<com.enershare.metering.domain.MeterReading> getReadings(@org.springframework.web.bind.annotation.PathVariable String smartMeterId) {
        return meteringApplicationService.getReadings(smartMeterId);
    }

}