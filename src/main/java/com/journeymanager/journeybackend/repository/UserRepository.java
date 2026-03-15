package com.journeymanager.journeybackend.repository;

import com.journeymanager.journeybackend.model.user.User;
import com.journeymanager.journeybackend.entity.Tenant;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Used during JWT login.
     * Fetch Tenant eagerly to avoid LazyInitializationException.
     */
    @EntityGraph(attributePaths = {"tenant"})
    Optional<User> findByEmail(String email);

    /**
     * Used for tenant-specific queries.
     * Tenant already known so no need for entity graph here.
     */
    Optional<User> findByEmailAndTenant(String email, Tenant tenant);
}