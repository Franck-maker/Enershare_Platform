package com.enershare.trading.infrastructure;

import com.enershare.trading.domain.TradingSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface TradingSessionRepository extends JpaRepository<TradingSession, UUID> {}