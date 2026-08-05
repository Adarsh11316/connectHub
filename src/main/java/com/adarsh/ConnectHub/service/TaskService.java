package com.adarsh.ConnectHub.service;

import com.adarsh.ConnectHub.dto.TaskRequest;
import com.adarsh.ConnectHub.dto.UpdateTaskStatusRequest;
import com.adarsh.ConnectHub.entity.Project;
import com.adarsh.ConnectHub.entity.Task;
import com.adarsh.ConnectHub.entity.User;
import com.adarsh.ConnectHub.enums.TaskStatus;
import com.adarsh.ConnectHub.repository.ProjectRepository;
import com.adarsh.ConnectHub.repository.TaskRepository;
import com.adarsh.ConnectHub.repository.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class TaskService {

    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(ProjectRepository projectRepository, TaskRepository taskRepository, UserRepository userRepository){
        this.projectRepository = projectRepository;
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }


    public User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }


    public Project getCurrentProject(Long projectId) {
        return projectRepository.findByIdAndOwner(projectId, getCurrentUser()).orElseThrow(
                () -> new RuntimeException("Project not found")
        );
    }

    public Task getCurrentTask(Long id,Long projectId) {
        return taskRepository.findByIdAndProject(id, getCurrentProject(projectId)).orElseThrow(
                () -> new RuntimeException("Task not found")
        );
    }

    public Task createTask(Long projectId, TaskRequest request) {
        Project project = getCurrentProject(projectId);
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setStatus(TaskStatus.TODO);
        task.setPriority(request.getPriority());
        task.setProject(project);

        return taskRepository.save(task);
    }

    public Page<Task> getAllTask(Long projectId,int page,int size,String sortBy,String direction) {
        Project project = getCurrentProject(projectId);

        Sort sort = direction.equalsIgnoreCase("desc")
                ? Sort.by(sortBy).descending()
                : Sort.by(sortBy).ascending();

        Pageable pageable = PageRequest.of(page, size,sort);

        return taskRepository.findByProject(project,pageable);
    }

    public Task getTaskById(Long id,Long projectId) {
        return getCurrentTask(id, projectId);
    }

    public Task updateTaskById(Long id,Long projectId,TaskRequest request) {
        Task task = getCurrentTask(id,projectId);

        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setPriority(request.getPriority());

        return taskRepository.save(task);
    }

    public Task updateTaskStatus(Long id, Long projectId, UpdateTaskStatusRequest request) {
        Task task = getCurrentTask(id, projectId);
        task.setStatus(request.getStatus());

        return taskRepository.save(task);
    }

    public Task deleteTaskById(Long id, Long projectId) {
        Task task = getCurrentTask(id, projectId);
        taskRepository.delete(task);
        return task;
    }

    public List<Task> searchTasks(Long projectId,String keyword) {
        Project project = getCurrentProject(projectId);
        return taskRepository.findByProjectAndTitleContainingIgnoreCase(project, keyword);
    }

    public Task assignTask(Long projectId, Long taskId, Long userId) {

        Task task = getCurrentTask(taskId, projectId);
        Project project = task.getProject();

        User user = userRepository.findById(userId).orElseThrow(
                ()->new RuntimeException("user not found")
        );

        if (!user.getId().equals(project.getOwner().getId())) {
            throw new RuntimeException("only project owner can be assignes");
        }
        task.setAssignedUser(user);
        return taskRepository.save(task);

    }
}
