package com.journeymanager.journeybackend.repository;

import com.journeymanager.journeybackend.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    List<User> findByTenantId(Long tenantId);
}