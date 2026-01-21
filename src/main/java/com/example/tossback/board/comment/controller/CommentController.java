package com.example.tossback.board.comment.controller;

import com.example.tossback.board.comment.dto.CommentRequestDTO;
import com.example.tossback.board.comment.service.CommentService;
import com.example.tossback.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }


    @PostMapping
    public ResponseEntity<CommonResponse> pushComment(@RequestBody CommentRequestDTO commentRequestDTO) {
        return new ResponseEntity<>(new CommonResponse(commentService.createComment(commentRequestDTO)), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getComment(@RequestParam long boardId) {
        return new ResponseEntity<>(new CommonResponse(commentService.getComment(boardId)),HttpStatus.OK);
    }
}
