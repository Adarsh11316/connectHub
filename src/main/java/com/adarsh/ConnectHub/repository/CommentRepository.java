package com.adarsh.ConnectHub.repository;

import com.adarsh.ConnectHub.entity.Comment;
import com.adarsh.ConnectHub.entity.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CommentRepository extends JpaRepository<Comment, Long> {
    public Page<Comment> findByTask(Task task, Pageable pageable);

    public Optional<Comment> findByIdAndTask(Long id, Task task);

}
