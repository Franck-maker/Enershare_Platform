package com.enershare.trading.infrastructure;

import com.enershare.trading.domain.Trade;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;
import java.util.List;

public interface TradeRepository extends JpaRepository<Trade, UUID> {
    List<Trade> findByBuyerId(UUID buyerId);
    List<Trade> findBySellerId(UUID sellerId);
}
