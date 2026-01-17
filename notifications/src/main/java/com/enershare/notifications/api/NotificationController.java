package com.enershare.notifications.api;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    @PostMapping("/send")
    public void sendNotification(@RequestBody NotificationRequest request) {
        // In a real system, this would send an email or push notification.
        // For now, we just log it as per requirements for "simulation" or testing.
        log.info("📧 NOTIFICATION to Application User [{}]: {}", request.recipientId(), request.message());
    }

    public record NotificationRequest(String recipientId, String message) {}
}
