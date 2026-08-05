package com.adarsh.ConnectHub.controller;


import com.adarsh.ConnectHub.dto.TaskRequest;
import com.adarsh.ConnectHub.dto.UpdateTaskStatusRequest;
import com.adarsh.ConnectHub.entity.Task;
import com.adarsh.ConnectHub.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/projects")
public class TaskController {
    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping("{projectId}")
    public Task createTask(@Valid @RequestBody TaskRequest request, @PathVariable Long projectId) {
        return taskService.createTask(projectId,request);
    }


    @GetMapping("{projectId}")
    public Page<Task> getAllTask(@PathVariable Long projectId, @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size, @RequestParam(defaultValue = "createdAt") String sortBy, @RequestParam(defaultValue = "desc") String direction) {
        return taskService.getAllTask(projectId, page, size,sortBy,direction);
    }

    @GetMapping("{projectId}/task/{id}")
    public Task getTaskById(@PathVariable Long projectId,@PathVariable Long id) {
        return taskService.getTaskById(id, projectId);
    }

    @PutMapping("{projectId}/task/{id}")
    public Task updateTaskById(@PathVariable Long projectId,@PathVariable Long id,@Valid @RequestBody TaskRequest request) {
        return taskService.updateTaskById(id, projectId, request);
    }

    @PutMapping("{projectId}/status/{id}")
    public Task updateTaskStatus(@PathVariable Long projectId,@PathVariable Long id,@RequestBody UpdateTaskStatusRequest request) {
        return taskService.updateTaskStatus(id, projectId, request);
    }


    @DeleteMapping("{projectId}/task/{id}")
    public Task deleteTaskById(@PathVariable Long projectId,@PathVariable Long id) {
        return taskService.deleteTaskById(id, projectId);
    }

    @GetMapping("{projectId}/search")
    public List<Task> searchTasks(@PathVariable Long projectId,@RequestParam String keyword) {
        return taskService.searchTasks(projectId, keyword);
    }

    @PostMapping("{projectId}/task/{taskId}/assign")
    public Task assignTask(@PathVariable Long projectId, @PathVariable Long taskId, @RequestParam Long userId) {
        return taskService.assignTask(projectId, taskId,userId);
    }
}
