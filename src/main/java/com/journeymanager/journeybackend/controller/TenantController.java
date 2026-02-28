package com.journeymanager.journeybackend.controller;

import com.journeymanager.journeybackend.entity.Tenant;
import com.journeymanager.journeybackend.service.TenantService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private final TenantService tenantService;

    public TenantController(TenantService tenantService) {
        this.tenantService = tenantService;
    }

    // CREATE TENANT
    @PostMapping
    public Tenant createTenant(@RequestBody Tenant tenant) {
        return tenantService.createTenant(tenant);
    }

    // GET ALL TENANTS
    @GetMapping
    public List<Tenant> getAllTenants() {
        return tenantService.getAllTenants();
    }

    // GET TENANT BY ID
    @GetMapping("/{id}")
    public Tenant getTenantById(@PathVariable Long id) {
        return tenantService.getTenantById(id);
    }

    // GET TENANT BY SUBDOMAIN
    @GetMapping("/subdomain/{subdomain}")
    public Tenant getTenantBySubdomain(@PathVariable String subdomain) {
        return tenantService.getTenantBySubdomain(subdomain);
    }
}
