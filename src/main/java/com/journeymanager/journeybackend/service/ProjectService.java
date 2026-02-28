package com.journeymanager.journeybackend.service;

import com.journeymanager.journeybackend.dto.ProjectResponse;
import com.journeymanager.journeybackend.entity.Project;
import com.journeymanager.journeybackend.entity.Tenant;
import com.journeymanager.journeybackend.tenant.TenantContext;
import com.journeymanager.journeybackend.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public List<ProjectResponse> getProjectsForCurrentTenant() {

        Long tenantId = TenantContext.getTenantId();

        if (tenantId == null) {
            throw new RuntimeException("Tenant header missing");
        }

        Tenant tenant = new Tenant();
        tenant.setId(tenantId);

        return projectRepository.findByTenant(tenant)
                .stream()
                .map(p -> new ProjectResponse(
                        p.getId(),
                        p.getName(),
                        p.getCreatedAt()
                ))
                .toList();
    }

    public ProjectResponse createProject(Project project) {

        Long tenantId = TenantContext.getTenantId();

        if (tenantId == null) {
            throw new RuntimeException("Tenant header missing");
        }

        Tenant tenant = new Tenant();
        tenant.setId(tenantId);

        project.setTenant(tenant);

        Project saved = projectRepository.save(project);

        return new ProjectResponse(
                saved.getId(),
                saved.getName(),
                saved.getCreatedAt()
        );
    }
}