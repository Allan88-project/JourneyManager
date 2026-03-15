package com.journeymanager.journeybackend.tenant.subscription;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "SUBSCRIPTIONS")
public class Subscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long tenantId;

    @Enumerated(EnumType.STRING)
    private SubscriptionPlan plan;

    private LocalDateTime startDate;

    private LocalDateTime endDate;

    private boolean active;

    public Long getId() {
        return id;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public SubscriptionPlan getPlan() {
        return plan;
    }

    public boolean isActive() {
        return active;
    }
}