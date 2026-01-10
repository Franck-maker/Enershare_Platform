package com.enershare.community;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * <h2>Community Context (Bounded Context)</h2>
 * <p>
 * This service acts as the <b>Identity & Access Management (IAM)</b> and Asset Registry for the platform.
 * </p>
 * <ul>
 * <li><b>Responsibility:</b> Manages Households (Users), Agents, and Smart Meter definitions.</li>
 * <li><b>Database:</b> PostgreSQL (Relational integrity is required for identity).</li>
 * <li><b>Communication:</b> Publishes events when a new Household or Meter is registered.</li>
 * </ul>
 */
@SpringBootApplication
public class CommunityApplication {
    public static void main(String[] args) {
        SpringApplication.run(CommunityApplication.class, args);
    }
}