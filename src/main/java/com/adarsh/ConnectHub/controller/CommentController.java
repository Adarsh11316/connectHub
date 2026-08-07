package com.adarsh.ConnectHub.controller;


import com.adarsh.ConnectHub.dto.CreateCommentRequest;
import com.adarsh.ConnectHub.dto.UpdateCommentRequest;
import com.adarsh.ConnectHub.entity.Comment;
import com.adarsh.ConnectHub.service.CommentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/projects")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping("{projectId}/task/{taskId}/comment")
    public Comment createComment(@PathVariable Long projectId,@PathVariable Long taskId, @Valid @RequestBody CreateCommentRequest request) {
        return commentService.createComment(projectId,taskId,request);
    }

    @GetMapping("{projectId}/task/{taskId}/comments")
    public Page<Comment> getAllComments(@PathVariable Long projectId,@PathVariable Long taskId,@RequestParam int page,@RequestParam int size) {
        return commentService.getAllComments( projectId,taskId,page,size);
    }

    @PutMapping("{projectId}/task/{taskId}/comment/{commentId}")
    public Comment updateComment(@PathVariable Long projectId,@PathVariable Long taskId,@PathVariable Long commentId,@Valid @RequestBody UpdateCommentRequest request) {
        return commentService.updateComment( projectId,taskId,commentId,request);
    }


    @DeleteMapping("{projectId}/task/{taskId}/comment/{commentId}")
    public Comment deleteComment(@PathVariable Long projectId,@PathVariable Long taskId,@PathVariable Long commentId) {
        return commentService.deleteComment(projectId, taskId,commentId);
    }
}
