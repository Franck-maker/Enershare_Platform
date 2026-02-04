package com.enershare.metering.application;

import org.springframework.stereotype.Service;
import com.enershare.metering.domain.*;

import com.enershare.metering.infrastructure.MeterReadingRepository;
import java.time.*; 
import lombok.*; 

@Service
@RequiredArgsConstructor

public class MeteringApplicationService {
    private final MeterReadingRepository meterReadingRepository;

    public void record(RecordReadingCommand command){
        MeterReading meterReading = MeterReading.builder()
        .smartMeterId(command.getSmartMeterId())
        .kwh(command.getKwh())
        .timestamp(Instant.now())
        .build();

        meterReadingRepository.save(meterReading); 
    }

    public java.util.List<MeterReading> getReadings(String smartMeterId) {
        return meterReadingRepository.findBySmartMeterId(smartMeterId);
    }
  
}
