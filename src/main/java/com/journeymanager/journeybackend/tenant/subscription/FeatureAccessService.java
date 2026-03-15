package com.journeymanager.journeybackend.tenant.subscription;

import org.springframework.stereotype.Service;

@Service
public class FeatureAccessService {

    private final SubscriptionService subscriptionService;

    public FeatureAccessService(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    public boolean isEnabled(Feature feature) {

        SubscriptionPlan plan = subscriptionService.getCurrentPlan();

        return switch (feature) {

            case TRIP_REPLAY ->
                    plan == SubscriptionPlan.PRO ||
                            plan == SubscriptionPlan.ENTERPRISE;

            case ADVANCED_ANALYTICS ->
                    plan == SubscriptionPlan.PRO ||
                            plan == SubscriptionPlan.ENTERPRISE;

            case FLEET_MONITORING ->
                    plan == SubscriptionPlan.ENTERPRISE;
        };
    }
}
