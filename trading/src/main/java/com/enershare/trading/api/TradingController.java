package com.enershare.trading.api;

import com.enershare.trading.application.TradingEngineService;
import com.enershare.trading.domain.Bid;
import com.enershare.trading.domain.Offer;
import com.enershare.trading.domain.TradingSession;
import com.enershare.trading.infrastructure.BidRepository;
import com.enershare.trading.infrastructure.OfferRepository;
import com.enershare.trading.infrastructure.TradingSessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/trading")
@RequiredArgsConstructor
public class TradingController {

    private final TradingEngineService tradingService;
    private final TradingSessionRepository sessionRepository;
    private final OfferRepository offerRepository; 
    private final BidRepository bidRepository;    

    // --- Offer handler ---
    @PostMapping("/offers")
    @ResponseStatus(HttpStatus.CREATED)
    public Offer createOffer(@RequestBody Offer offer) {
        //we should check if the user has produce energy but...
        offer.setStatus(Offer.OfferStatus.OPEN);
        // if CreatedAT is null, set it to now
        if (offer.getCreatedAt() == null) {
            offer.setCreatedAt(Instant.now());
        }
        return offerRepository.save(offer);
    }

    // --- Bid handler ---
    @PostMapping("/bids")
    @ResponseStatus(HttpStatus.CREATED)
    public Bid createBid(@RequestBody Bid bid) {
        bid.setStatus(Bid.BidStatus.OPEN);
        if (bid.getCreatedAt() == null) {
            bid.setCreatedAt(Instant.now());
        }
        return bidRepository.save(bid);
    }

    // --- Session Handler ---
    @PostMapping("/sessions")
    @ResponseStatus(HttpStatus.CREATED)
    public TradingSession createSession() {
        TradingSession session = TradingSession.builder()
                .startTime(Instant.now())
                .status(TradingSession.SessionStatus.OPEN)
                .build();
        return sessionRepository.save(session);
    }

    @GetMapping("/sessions/active")
    public List<TradingSession> getActiveSessions() {
        return sessionRepository.findAll().stream()
                .filter(s -> s.getStatus() == TradingSession.SessionStatus.OPEN)
                .toList();
    }

    // launch a mtching according to a section
    @PostMapping("/sessions/{id}/close")
    public String closeSessionAndRunMatching(@PathVariable UUID id) {
        TradingSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        session.setStatus(TradingSession.SessionStatus.CLOSED);
        session.setEndTime(Instant.now());
        sessionRepository.save(session);

        // Launch engine
        tradingService.runMatchingEngine();

        return "Session " + id + " closed and matching engine executed.";
    }
}