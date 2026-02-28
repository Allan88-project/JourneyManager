package com.journeymanager.journeybackend.service;

import com.journeymanager.journeybackend.dto.UserRequest;
import com.journeymanager.journeybackend.dto.UserResponse;
import com.journeymanager.journeybackend.entity.Tenant;
import com.journeymanager.journeybackend.model.user.User;
import com.journeymanager.journeybackend.repository.UserRepository;
import com.journeymanager.journeybackend.tenant.TenantContext;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getUsersForCurrentTenant() {
        Long tenantId = TenantContext.getTenantId();

        return userRepository.findByTenantId(tenantId)
                .stream()
                .map(user -> new UserResponse(
                        user.getId(),
                        user.getName(),
                        user.getEmail()
                ))
                .toList();
    }

    public UserResponse createUser(UserRequest request) {

        Long tenantId = TenantContext.getTenantId();

        Tenant tenant = new Tenant();
        tenant.setId(tenantId);

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setTenant(tenant);

        User saved = userRepository.save(user);

        return new UserResponse(
                saved.getId(),
                saved.getName(),
                saved.getEmail()
        );
    }
}