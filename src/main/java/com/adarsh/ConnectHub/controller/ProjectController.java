package com.adarsh.ConnectHub.controller;

import com.adarsh.ConnectHub.dto.CreateProjectRequest;
import com.adarsh.ConnectHub.entity.Project;
import com.adarsh.ConnectHub.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/project")
public class ProjectController {
    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public Project createProject(@Valid @RequestBody CreateProjectRequest request) {
        return projectService.createProject(request);
    }

    @GetMapping
    public List<Project> getAllProject() {
        return projectService.getAllProjects();
    }

    @GetMapping("{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.getProjectById(id);
    }

    @PutMapping("{id}")
    public Project updateProjectById(@PathVariable Long id,@RequestBody CreateProjectRequest request) {
        return projectService.updateProjectById(id,request);
    }

    @DeleteMapping("{id}")
    public Project deleteProjectById(@PathVariable Long id) {
        return projectService.deleteProjectById(id);
    }
}
