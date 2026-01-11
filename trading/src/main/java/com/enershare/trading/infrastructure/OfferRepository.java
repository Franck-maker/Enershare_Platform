package com.enershare.trading.infrastructure;

import java.util.*;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enershare.trading.domain.Offer;

@Repository
public interface OfferRepository extends JpaRepository<Offer, UUID> {
    List<Offer> findByStatusOrderByCreatedAtAsc(Offer.OfferStatus status);
    
}
