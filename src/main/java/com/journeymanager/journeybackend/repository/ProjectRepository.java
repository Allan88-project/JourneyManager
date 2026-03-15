package com.journeymanager.journeybackend.repository;

import com.journeymanager.journeybackend.entity.Project;
import com.journeymanager.journeybackend.entity.Tenant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByTenant(Tenant tenant);
}

