package com.example.tossback.board.service.impl;

import com.example.tossback.board.boardLike.repository.BoardLikeRepository;
import com.example.tossback.board.dto.BoardRequestDTO;
import com.example.tossback.board.dto.BoardResponseDTO;
import com.example.tossback.board.dto.BoardResultDTO;
import com.example.tossback.board.entity.Board;
import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.board.repository.BoardRepository;
import com.example.tossback.board.service.BoardService;
import com.example.tossback.common.enums.ErrorCode;
import com.example.tossback.common.exception.CommonException;
import com.example.tossback.config.redis.util.RedisUtil;
import com.example.tossback.member.dto.MemberResponseDTO;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final RedisUtil redisUtil;
    private final MemberRepository memberRepository;
    private final BoardLikeRepository boardLikeRepository;

    public BoardServiceImpl(BoardRepository boardRepository, RedisUtil redisUtil, MemberRepository memberRepository, BoardLikeRepository boardLikeRepository) {
        this.boardRepository = boardRepository;
        this.redisUtil = redisUtil;
        this.memberRepository = memberRepository;
        this.boardLikeRepository = boardLikeRepository;
    }

    @Override
    public BoardResultDTO getBoards(BoardCode boardCode, String searchKeyword, int size, int page, String sort) {
        BoardResultDTO result = new BoardResultDTO();

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("id").ascending());

        Page<Board> boardPage = boardRepository.searchBoards(boardCode, searchKeyword, pageable, sort);
        List<BoardResponseDTO> response = boardPage.getContent().stream().map(board -> {
            BoardResponseDTO boardResponseDTO = new BoardResponseDTO();
            boardResponseDTO.setId(board.getId());
            boardResponseDTO.setTitle(board.getTitle());
            boardResponseDTO.setCreatedAt(board.getCreatedAt());
            boardResponseDTO.setViewCount(board.getViewCount());
            boardResponseDTO.setLikeCount(board.getLikeCount());
            return boardResponseDTO;
        }).toList();
        result.setBoards(response);
        result.setTotalCount(boardPage.getTotalElements());

        return result;
    }

    @Override
    @Transactional
    public BoardResponseDTO getBoardDetail(long boardCode, String userId) {

        Board board = boardRepository.findById(boardCode);

        if (board == null) {
            throw new IllegalArgumentException("존재하지 않는 게시글입니다. ID: " + boardCode);
        }

        String redisKey = "view:board:" + boardCode + ":user:" + userId;
        String isViewed = redisUtil.getData(redisKey);
        if (isViewed == null) {
            board.incrementViewCount();
            redisUtil.setDataExpire(redisKey, "visited", 86400);
        }

        boolean isLiked = false;
        boolean canDelete = false;

        if (userId != null && !userId.equals("anonymousUser")) {
            Member currentId = memberRepository.findByUserId(userId);
            if (currentId != null) {
                isLiked = boardLikeRepository.existsByBoardAndMember(board, currentId);
                if ("ROLE_ADMIN".equals(currentId.getUserRoleType().name()) ||
                        board.getMember().getUserId().equals(currentId.getUserId())) {
                    canDelete = true;
                }
            }
        }

        Member member = board.getMember();
        MemberResponseDTO memberResponseDTO = MemberResponseDTO.from(member);

        BoardResponseDTO result = new BoardResponseDTO();
        result.setId(board.getId());
        result.setTitle(board.getTitle());
        result.setContent(board.getContent());
        result.setCreatedAt(board.getCreatedAt());
        result.setLikeCount(board.getLikeCount());
        result.setViewCount(board.getViewCount());
        result.setMember(memberResponseDTO);
        result.setBoardCode(board.getBoardType().getBoardCode());
        result.setLiked(isLiked);
        result.setCanDelete(canDelete);

        return result;
    }

    @Override
    public BoardResponseDTO updateLike(long boardId) {
        BoardResponseDTO result = new BoardResponseDTO();
        Board board = boardRepository.findById(boardId);
        board.setLikeCount(board.getLikeCount() + 1);
        boardRepository.save(board);

        result.setId(board.getId());
        result.setTitle(board.getTitle());
        result.setContent(board.getContent());
        result.setCreatedAt(board.getCreatedAt());
        result.setLikeCount(board.getLikeCount());
        result.setViewCount(board.getViewCount());
        return result;
    }

    @Transactional
    @Override
    public Boolean deleteBoard(BoardRequestDTO boardRequestDTO) {
        Board board = boardRepository.findBoardById(boardRequestDTO.getBoardId())
                .orElseThrow(() -> new CommonException("게시판 찾기 실패",ErrorCode.NOT_FOUND_DATA));
        String currentUserId = SecurityContextHolder.getContext().getAuthentication().getName();
        Member currentUser = memberRepository.findByUserId(currentUserId);
        boolean isAdmin = currentUser.getUserRoleType().name().equals("ROLE_ADMIN");
        boolean isAuthor = board.getMember().getUserId().equals(currentUser.getUserId());
        if (!isAdmin && !isAuthor) {
            throw new CommonException("삭제 권한이 없습니다.", ErrorCode.UNAUTHORIZED_ACCESS);
        }
        try {
             board.setDeletedAt(LocalDateTime.now());
            return true;
        } catch (Exception e) {
            throw new CommonException("삭제 중 오류가 발생했습니다.", ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }
}
