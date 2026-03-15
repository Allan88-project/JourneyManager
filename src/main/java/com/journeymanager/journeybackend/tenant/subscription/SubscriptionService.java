package com.journeymanager.journeybackend.tenant.subscription;

import com.journeymanager.journeybackend.tenant.TenantContext;
import org.springframework.stereotype.Service;

@Service
public class SubscriptionService {

    private final SubscriptionRepository repository;

    public SubscriptionService(SubscriptionRepository repository) {
        this.repository = repository;
    }

    public SubscriptionPlan getCurrentPlan() {

        Long tenantId = TenantContext.getTenantId();

        return repository.findByTenantIdAndActiveTrue(tenantId)
                .map(Subscription::getPlan)
                .orElse(SubscriptionPlan.BASIC);
    }
}