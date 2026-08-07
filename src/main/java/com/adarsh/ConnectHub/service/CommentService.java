package com.adarsh.ConnectHub.service;

import com.adarsh.ConnectHub.dto.CreateCommentRequest;
import com.adarsh.ConnectHub.dto.UpdateCommentRequest;
import com.adarsh.ConnectHub.entity.Comment;
import com.adarsh.ConnectHub.entity.Task;
import com.adarsh.ConnectHub.entity.User;
import com.adarsh.ConnectHub.repository.CommentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentRepository commentRepository;
    private final TaskService taskService;

    public CommentService(CommentRepository commentRepository, TaskService taskService) {
        this.commentRepository = commentRepository;
        this.taskService = taskService;
    }

    public Comment getCurrentComment( Long projectId, Long taskId,Long commentId) {
        Task task = taskService.getCurrentTask(taskId, projectId);
        return commentRepository.findByIdAndTask(commentId, task).orElseThrow(
                () -> new RuntimeException("Comment not found")
        );
    }

    public Comment createComment(Long projectId,Long taskId,CreateCommentRequest request) {
        User currentUser = taskService.getCurrentUser();
        Task task=taskService.getCurrentTask(taskId,projectId);
        Comment comment = new Comment();
        comment.setMessage(request.getMessage());
        comment.setAuthor(currentUser);
        comment.setTask(task);
        return commentRepository.save(comment);
    }

    public Page<Comment> getAllComments(
            Long projectId,
            Long taskId,
            int page,
            int size) {

        Task task = taskService.getCurrentTask(taskId, projectId);
        Pageable pageable = PageRequest.of(page, size);
        return commentRepository.findByTask(task, pageable);

    }

    public Comment updateComment(Long projectId, Long taskId,Long commentId,UpdateCommentRequest request) {
        Comment comment = getCurrentComment(projectId,taskId,commentId);

        if (!comment.getAuthor().getId().equals(taskService.getCurrentUser().getId())) {
            throw new RuntimeException("you can only edit your own comment");
        }
        comment.setMessage(request.getMessage());

        return commentRepository.save(comment);
    }

    public Comment deleteComment(Long projectId,Long taskId,Long commentId) {
        Comment comment = getCurrentComment( projectId, taskId,commentId);
        if (!comment.getAuthor().getId().equals(taskService.getCurrentUser().getId())) {
            throw new RuntimeException("you can delete only your own comment");
        }
        commentRepository.delete(comment);
        return comment;
    }
}
