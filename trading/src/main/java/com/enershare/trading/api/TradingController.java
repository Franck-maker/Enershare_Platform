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
    private final com.enershare.trading.infrastructure.TradeRepository tradeRepository;
    private final com.enershare.trading.application.CommunityGateway communityGateway;

    // --- Offer handler ---
    @PostMapping("/offers")
    @ResponseStatus(HttpStatus.CREATED)
    public Offer createOffer(@RequestBody Offer offer) {
        validateActiveSession();
        
        // ROLE CHECK: Only PROSUMERs can sell
        boolean isProsumer = communityGateway.isProsumer(offer.getSellerId());
        if (!isProsumer) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only PROSUMERs can create Sales Offers.");
        }
        
        
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
        validateActiveSession();
        bid.setStatus(Bid.BidStatus.OPEN);
        if (bid.getCreatedAt() == null) {
            bid.setCreatedAt(Instant.now());
        }
        return bidRepository.save(bid);
    }

    // --- Session Handler ---
    // POST /api/trading/sessions
    @PostMapping("/sessions")
    @ResponseStatus(HttpStatus.CREATED)
    public TradingSession createSession(@RequestBody(required = false) TradingSession session) {
        // Validation: Ensure no other session is currently OPEN
        boolean hasOpenSession = sessionRepository.findAll().stream()
                .anyMatch(s -> s.getStatus() == TradingSession.SessionStatus.OPEN);
        
        if (hasOpenSession) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "An active trading session already exists.");
        }

        if (session == null) session = new TradingSession();
        
        session.setStatus(TradingSession.SessionStatus.OPEN);
        if (session.getStartTime() == null) {
            session.setStartTime(Instant.now());
        }
        return sessionRepository.save(session);
    }

    @GetMapping("/active")
    public List<TradingSession> getActiveSessions() {
        return sessionRepository.findAll().stream()
                .filter(s -> s.getStatus() == TradingSession.SessionStatus.OPEN)
                .toList();
    }
    
    // --- History Handler ---
    @GetMapping("/trades")
    public List<com.enershare.trading.domain.Trade> getAllTrades() {
        return tradeRepository.findAll();
    }

    // launch a matching according to a session
    @PostMapping("/sessions/{id}/close")
    public String closeSessionAndRunMatching(@PathVariable UUID id) {
        TradingSession session = sessionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));

        if (session.getStatus() == TradingSession.SessionStatus.CLOSED) {
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Session already closed.");
        }

        session.setStatus(TradingSession.SessionStatus.CLOSED);
        session.setEndTime(Instant.now());
        sessionRepository.save(session);

        // Launch engine
        tradingService.runMatchingEngine();

        return "Session " + id + " closed and matching engine executed.";
    }

    private void validateActiveSession() {
        boolean hasOpenSession = sessionRepository.findAll().stream()
                .anyMatch(s -> s.getStatus() == TradingSession.SessionStatus.OPEN);
        if (!hasOpenSession) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Trading market is currently closed.");
        }
    }
}