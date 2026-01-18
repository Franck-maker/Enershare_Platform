package com.enershare.trading.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class RestNotificationGateway {
    
    private final RestTemplate restTemplate;

    @Value("${services.notifications.url:http://enershare-notifications:8085}")
    private String notificationUrl;

    public void sendTradeNotification(String recipientId, String message) {
        try {
            String url = notificationUrl + "/api/notifications/send";
            var request = new NotificationRequest(recipientId, message);
            restTemplate.postForObject(url, request, Void.class);
        } catch (Exception e) {
            System.err.println("Failed to send notification: " + e.getMessage());
        }
    }

    public record NotificationRequest(String recipientId, String message) {}
}
