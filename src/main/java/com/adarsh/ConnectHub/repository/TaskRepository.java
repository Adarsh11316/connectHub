package com.adarsh.ConnectHub.repository;

import com.adarsh.ConnectHub.entity.Project;
import com.adarsh.ConnectHub.entity.Task;
import com.adarsh.ConnectHub.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    public Page<Task> findByProject(Project project, Pageable pageable);

    public Optional<Task> findByIdAndProject(Long id, Project project);

    public List<Task> findByProjectAndTitleContainingIgnoreCase(Project project, String keyword);

    public List<Task> findByAssignedUser(User user);

}
