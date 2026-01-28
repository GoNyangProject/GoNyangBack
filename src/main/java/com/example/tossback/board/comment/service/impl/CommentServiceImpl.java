package com.example.tossback.board.comment.service.impl;

import com.example.tossback.board.comment.dto.CommentRequestDTO;
import com.example.tossback.board.comment.dto.CommentResponseDTO;
import com.example.tossback.board.comment.entity.Comment;
import com.example.tossback.board.comment.repository.CommentRepository;
import com.example.tossback.board.comment.service.CommentService;
import com.example.tossback.board.entity.Board;
import com.example.tossback.board.repository.BoardRepository;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.repository.MemberRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final BoardRepository boardRepository;

    public CommentServiceImpl(CommentRepository commentRepository, MemberRepository memberRepository, BoardRepository boardRepository) {
        this.commentRepository = commentRepository;
        this.memberRepository = memberRepository;
        this.boardRepository = boardRepository;
    }

    @Override
    public CommentResponseDTO createComment(CommentRequestDTO commentRequestDTO) {
        Comment parent = commentRepository.findById(commentRequestDTO.getParentId());
        Comment result = new Comment();
        Board board = boardRepository.findByBoardType_BoardCode(commentRequestDTO.getBoardCode());
        Member member = memberRepository.findById(commentRequestDTO.getMemberId());
        result.setBoard(board);
        result.setMember(member);
        result.setContent(commentRequestDTO.getContent());
        if (parent != null) {
            result.setParent(parent);
        }
        commentRepository.save(result);
        return null;
    }

    @Override
    public List<CommentResponseDTO> getComment(long boardId) {
        List<Comment> rootComments = commentRepository.findByBoardIdAndParentIsNull(boardId);

        return rootComments.stream()
                .map(CommentResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Override
    public boolean deleteComment(long commentId) {
        Comment comment = commentRepository.findById(commentId);
        if (comment != null) {
            commentRepository.deleteById(commentId);
        } else {
            return false;
        }
        return true;
    }
}
