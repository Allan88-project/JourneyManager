package com.journeymanager.journeybackend.service;

import com.journeymanager.journeybackend.entity.Tenant;
import com.journeymanager.journeybackend.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantService(TenantRepository tenantRepository) {
        this.tenantRepository = tenantRepository;
    }

    // CREATE
    public Tenant createTenant(Tenant tenant) {

        if (tenantRepository.findBySubdomain(tenant.getSubdomain()).isPresent()) {
            throw new RuntimeException("Subdomain already exists");
        }

        return tenantRepository.save(tenant);
    }

    // GET ALL
    public List<Tenant> getAllTenants() {
        return tenantRepository.findAll();
    }

    // GET BY ID
    public Tenant getTenantById(Long id) {
        return tenantRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
    }

    // GET BY SUBDOMAIN
    public Tenant getTenantBySubdomain(String subdomain) {
        return tenantRepository.findBySubdomain(subdomain)
                .orElseThrow(() -> new RuntimeException("Tenant not found"));
    }
}
