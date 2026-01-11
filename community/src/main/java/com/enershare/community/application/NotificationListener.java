package com.enershare.community.application;

import com.enershare.common.events.TradeMatchedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationListener {

    @EventListener
    public void handleTradeNotification(TradeMatchedEvent event) {
        //Receive alerts for trades
        System.out.println("🔔 NOTIFICATION to User " + event.getBuyerId() + ": You bought energy!");
        System.out.println("🔔 NOTIFICATION to User " + event.getSellerId() + ": You sold energy!");
        System.out.println("   Details: " + event.getKwhAmount() + "kWh for " + event.getTotalPrice() + "€");
    }
}