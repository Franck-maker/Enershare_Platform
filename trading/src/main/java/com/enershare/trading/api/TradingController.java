package com.enershare.trading.api;

import com.enershare.trading.application.TradingEngineService;
import com.enershare.trading.domain.TradingSession;
import com.enershare.trading.infrastructure.TradingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trading-sessions") 
@RequiredArgsConstructor
public class TradingController {

    private final TradingEngineService tradingService;
    private final TradingSessionRepository sessionRepository;

    //POST /tradingSessions (Create a new round)
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TradingSession createSession() {
        TradingSession session = TradingSession.builder()
                .startTime(Instant.now())
                .status(TradingSession.SessionStatus.OPEN)
                .build();
        return sessionRepository.save(session);
    }

    // GET /tradingSessions/active
    @GetMapping("/active")
    public List<TradingSession> getActiveSessions() {
        return sessionRepository.findAll().stream()
                .filter(s -> s.getStatus() == TradingSession.SessionStatus.OPEN)
                .toList();
    }

    //POST /tradingSessions/{id}/close (Triggers the matching engine!)
    @PostMapping("/{id}/close")
    public String closeSessionAndRunMatching(@PathVariable UUID id) {
        TradingSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        session.setStatus(TradingSession.SessionStatus.CLOSED);
        session.setEndTime(Instant.now());
        sessionRepository.save(session);

        // Run the matching logic
        tradingService.runMatchingEngine();

        return "Session " + id + " closed and matching engine executed.";
    }
}