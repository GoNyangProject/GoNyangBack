package com.example.tossback.board.comment.repository;

import com.example.tossback.board.comment.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    Comment findByParentId(long parentId);

    List<Comment> findByBoardId(long boardId);
}
