package com.example.tossback.board.boardLike.service.impl;

import com.example.tossback.board.boardLike.entity.BoardLike;
import com.example.tossback.board.boardLike.repository.BoardLikeRepository;
import com.example.tossback.board.boardLike.service.LikeService;
import com.example.tossback.board.dto.BoardRequestDTO;
import com.example.tossback.board.dto.BoardResponseDTO;
import com.example.tossback.board.entity.Board;
import com.example.tossback.board.repository.BoardRepository;
import com.example.tossback.common.enums.ErrorCode;
import com.example.tossback.common.exception.CommonException;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikeServiceImpl implements LikeService {

    private final BoardRepository boardRepository;
    private final MemberRepository memberRepository;
    private final BoardLikeRepository boardLikeRepository;

    @Transactional
    @Override
    public BoardResponseDTO toggleLike(BoardRequestDTO dto) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();
        Board board = boardRepository.findBoardById(dto.getBoardId())
                .orElseThrow(() -> new CommonException("게시글 조회 실패",ErrorCode.NOT_FOUND_DATA));
        Member member = memberRepository.findByUserId(userId);
        if (member == null) {
            log.error("해당 회원이 존재하지 않습니다");
            throw new CommonException("해당 회원이 존재하지 않습니다." , ErrorCode.NOT_FOUND_DATA);
        }
        Optional<BoardLike> existingLike = boardLikeRepository.findByBoardAndMember(board, member);
        boolean isLiked;
        if (existingLike.isPresent()) {
            boardLikeRepository.delete(existingLike.get());
            board.setLikeCount(Math.max(0, board.getLikeCount() - 1));
            isLiked = false;
        } else {
            log.info("새로운 좋아요 저장 시도");
            BoardLike newLike = new BoardLike();
            newLike.setBoard(board);
            newLike.setMember(member);
            boardLikeRepository.save(newLike);
            board.setLikeCount(board.getLikeCount() + 1);
            isLiked = true;
        }
        BoardResponseDTO response = BoardResponseDTO.fromEntity(board);
        response.setLiked(isLiked);
        response.setLikeCount(board.getLikeCount());

        return response;
    }
}
