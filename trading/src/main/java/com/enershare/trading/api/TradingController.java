package com.enershare.trading.api;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.enershare.trading.application.TradingEngineService;


import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api/tradings")
@RequiredArgsConstructor
public class TradingController {
    private final TradingEngineService tradingService;


    @PostMapping("/run-session")
    public String runMatchingSession(){
        tradingService.runMatchingEngine();
        return "Matching session finished";
    } 

    
}
