package com.enershare.trading.infrastructure;

import java.util.*;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.enershare.trading.domain.Bid;


@Repository
public interface BidRepository extends JpaRepository<Bid, UUID> {
    // find by status and order by FIFO
    List<Bid> findByStatusOrderByCreatedAtAsc(Bid.BidStatus status);
    
}
