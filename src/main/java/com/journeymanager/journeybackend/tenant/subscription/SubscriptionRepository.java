package com.journeymanager.journeybackend.tenant.subscription;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByTenantIdAndActiveTrue(Long tenantId);

}