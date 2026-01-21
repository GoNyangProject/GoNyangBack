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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        Comment parent = commentRepository.findByParentId(commentRequestDTO.getParentId());
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
        // 1. 해당 게시글의 모든 댓글을 한 번에 가져옴
        List<Comment> comments = commentRepository.findByBoardId(boardId);

        // 최종적으로 최상위 댓글만 담을 리스트
        List<CommentResponseDTO> result = new ArrayList<>();

        // 빠른 조회를 위해 ID를 키로 하는 Map 생성
        Map<Long, CommentResponseDTO> map = new HashMap<>();

        if (comments != null) {
            // 2. 먼저 모든 댓글을 DTO로 변환해서 Map에 채움 (이때 children은 비어있음)
            for (Comment comment : comments) {
                CommentResponseDTO dto = new CommentResponseDTO();
                dto.setId(comment.getId());
                dto.setContent(comment.getContent());
                dto.setWriter(comment.getMember().getUsername());
                dto.setCreatedAt(comment.getCreatedAt());
                dto.setChildren(new ArrayList<>()); // 자식 리스트 초기화

                map.put(dto.getId(), dto);
            }

            // 3. 다시 돌면서 부모-자식 관계 조립
            for (Comment comment : comments) {
                CommentResponseDTO dto = map.get(comment.getId());

                if (comment.getParent() == null) {
                    // 부모가 없으면 최상위 댓글임 -> 결과 리스트에 추가
                    result.add(dto);
                } else {
                    // 부모가 있으면 -> Map에서 부모 DTO를 꺼내서 그 자식(children)으로 추가
                    CommentResponseDTO parentDto = map.get(comment.getParent().getId());
                    if (parentDto != null) {
                        parentDto.getChildren().add(dto);
                    }
                }
            }
        }

        // 4. 최상위 댓글들만 반환 (안에 자식들이 줄줄이 달려있음)
        return result;
    }
}
