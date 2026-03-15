package com.journeymanager.journeybackend.controller;

import com.journeymanager.journeybackend.dto.ProjectResponse;
import com.journeymanager.journeybackend.entity.Project;
import com.journeymanager.journeybackend.service.ProjectService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @GetMapping
    public List<ProjectResponse> getProjects() {
        return projectService.getProjectsForCurrentTenant();
    }

    @PostMapping
    public ProjectResponse createProject(@RequestBody Project project) {
        return projectService.createProject(project);
    }
}