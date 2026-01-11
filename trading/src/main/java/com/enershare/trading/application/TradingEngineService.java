package com.enershare.trading.application;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;


import com.enershare.trading.infrastructure.BidRepository;
import com.enershare.trading.infrastructure.OfferRepository;
import com.enershare.metering.infrastructure.MeterReadingRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class TradingEngineService {
    private final OfferRepository offerRepository;
    private final BidRepository bidRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final MeterReadingRepository meterReadingRepository;


    /**
     * Triggered periodically (e.g., via Cron or Controller) to execute trades.
     */
    public void runMatchingEngine() {
        // Fetch all OPEN orders (FIFO sorted by repository)
        var offers = offerRepository.findByStatusOrderByCreatedAtAsc(com.enershare.trading.domain.Offer.OfferStatus.OPEN);
        var bids = bidRepository.findByStatusOrderByCreatedAtAsc(com.enershare.trading.domain.Bid.BidStatus.OPEN);

        System.out.println("Starting Matching Session: " + bids.size() + " Bids vs " + offers.size() + " Offers");

        // Iterate through Bids (Buyers)
        for (var bid : bids) {
            if (bid.getKwhAmount() <= 0) continue; // Skip if filled

            // Try to satisfy this Bid with available Offers
            for (var offer : offers) {
                
                // checking if the seller has producted enough energy by checking its readings
                boolean hasEnergy = meterReadingRepository.findBySmartMeterId(offer.getSellerId().toString())
                .stream()
                .mapToDouble(reading -> reading.getKwh())
                .sum() > offer.getKwhAmount();

                if (!hasEnergy) {
                    System.out.println("⚠️ Rule Violation: User " + offer.getSellerId() + " tried to sell without production.");
                    continue;
                }

                if (offer.getKwhAmount() <= 0) continue; // Skip if empty

                //CHECK PRICE: Does the buyer offer enough money?
                if (bid.getPricePerKwh() >= offer.getPricePerKwh()) {
                    
                    // MATCH FOUND! Calculate amount
                    double tradedAmount = Math.min(bid.getKwhAmount(), offer.getKwhAmount());
                    double tradePrice = offer.getPricePerKwh(); // Rule: Buyer pays the Seller's asking price

                    //Execute Logic
                    executeTrade(bid, offer, tradedAmount, tradePrice);

                    // If bid is fully satisfied, stop looking for offers for this specific bid
                    if (bid.getKwhAmount() == 0) {
                        break; 
                    }
                }
            }
        }
    }

    private void executeTrade(com.enershare.trading.domain.Bid bid, com.enershare.trading.domain.Offer offer, double amount, double price) {
        // Update Entities (Decreasing remaining amount)
        bid.setKwhAmount(bid.getKwhAmount() - amount);
        offer.setKwhAmount(offer.getKwhAmount() - amount);

        //  Update Status if fully matched
        if (bid.getKwhAmount() == 0) {
            bid.setStatus(com.enershare.trading.domain.Bid.BidStatus.MATCHED);
        }
        if (offer.getKwhAmount() == 0) {
            offer.setStatus(com.enershare.trading.domain.Offer.OfferStatus.MATCHED);
        }

        //Save changes to DB (Transactional will commit this at the end)
        bidRepository.save(bid);
        offerRepository.save(offer);

        // Publish Event for the Wallet Service
        var event = com.enershare.common.events.TradeMatchedEvent.builder()
                .tradeId(java.util.UUID.randomUUID())
                .sellerId(offer.getSellerId())
                .buyerId(bid.getBuyerId())
                .kwhAmount(amount)
                .pricePerKwh(price)
                .totalPrice(amount * price)
                .build();

        eventPublisher.publishEvent(event);
        
        System.out.println("Trade Executed! " + amount + "kWh sold for " + (amount * price) + "€");
    }


    
}
