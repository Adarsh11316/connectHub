package com.adarsh.ConnectHub.service;

import com.adarsh.ConnectHub.dto.CreateProjectRequest;
import com.adarsh.ConnectHub.entity.Project;
import com.adarsh.ConnectHub.entity.Task;
import com.adarsh.ConnectHub.entity.User;
import com.adarsh.ConnectHub.repository.ProjectRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;


    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public Project createProject(CreateProjectRequest request) {
        User currentUser = getCurrentUser();

        Project project = new Project();

        project.setName(request.getName());
        project.setDescription(request.getDescription());
        project.setOwner(currentUser);

        return projectRepository.save(project);

    }
    public List<Project> getAllProjects(){
        User currentUser = getCurrentUser();
        return projectRepository.findByOwner(currentUser);
    }

    public Project getProjectById(Long id) {
        User currentUser = getCurrentUser();
        return projectRepository.findByIdAndOwner(id, currentUser)
                .orElseThrow(
                        ()-> new RuntimeException("project not found")
                );
    }

    public Project updateProjectById(Long id,CreateProjectRequest request) {
        User currentUser = getCurrentUser();
        Project project = projectRepository.findByIdAndOwner(id, currentUser).orElseThrow(
                () -> new RuntimeException("Project not found")
        );

        project.setName(request.getName());
        project.setDescription(request.getDescription());

        return projectRepository.save(project);
    }

    public Project deleteProjectById(Long id) {
        User currentUser = getCurrentUser();

        Project project = projectRepository.findByIdAndOwner(id, currentUser).orElseThrow(
                () -> new RuntimeException("Project Not found")
        );

        projectRepository.delete(project);
        return project;

    }

}
