package com.example.tossback.community.service.impl;

import com.example.tossback.board.dto.BoardResponseDTO;
import com.example.tossback.board.entity.Board;
import com.example.tossback.board.entity.BoardType;
import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.board.repository.BoardRepository;
import com.example.tossback.board.repository.BoardTypeRepository;
import com.example.tossback.community.dto.CommunityListResponse;
import com.example.tossback.community.dto.CommunitySaveRequest;
import com.example.tossback.community.dto.FileUploadResponseDto;
import com.example.tossback.community.service.CommunityService;
import com.example.tossback.member.entity.Member;
import com.example.tossback.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CommunityServiceImpl implements CommunityService {
    private final BoardRepository boardRepository;
    private final BoardTypeRepository boardTypeRepository;
    private final MemberRepository memberRepository;
    @Value("${spring.file.storage.path}")
    private String storagePath;

    @Override
    public CommunityListResponse getCommunityList(int page, int size, BoardCode boardCode) {
        Sort sort = Sort.by(Sort.Order.desc("createdAt"));
        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Board> boardPage = boardRepository.findCommunityListWithFilters(pageable, boardCode);

        CommunityListResponse response = new CommunityListResponse();

        List<BoardResponseDTO> boardDtos = boardPage.getContent().stream()
                .map(BoardResponseDTO::fromEntity)
                .collect(Collectors.toList());

        response.setBoards(boardDtos);
        response.setTotalPages(boardPage.getTotalPages());
        response.setTotalElements(boardPage.getTotalElements());

        return response;
    }

    @Override
    public FileUploadResponseDto uploadFile(MultipartFile file) {
        if (file.isEmpty()) return null;

        try {
            String categoryPath = "community/";
            File directory = new File(storagePath, categoryPath);

            if (!directory.exists()) {
                directory.mkdirs();
            }
            String originalFilename = file.getOriginalFilename();
            String uuid = UUID.randomUUID().toString();
            String ext = extractExt(originalFilename);
            String savedFileName = uuid + "." + ext;

            File targetFile = new File(directory.getAbsolutePath(), savedFileName);

            file.transferTo(targetFile);
            String fileUrl = "http://localhost:8080/uploads/" + categoryPath + savedFileName;

            return FileUploadResponseDto.builder()
                    .src(fileUrl)
                    .fileName(originalFilename)
                    .build();

        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("파일 저장 중 상세 오류: " + e.getMessage());
        }
    }

    @Transactional
    @Override
    public Boolean saveCommunity(CommunitySaveRequest saveRequest) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getName();

        Member member = memberRepository.findByUserId(userId);
        if (member == null) {
            log.error("해당 회원이 존재하지 않습니다");
            throw new RuntimeException("해당 회원이 존재하지 않습니다.");
        }
        BoardType boardType = boardTypeRepository.findByBoardCode(BoardCode.valueOf(saveRequest.getBoardCode()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시판입니다."));

        Board board = boardRepository.findById(saveRequest.getBoardId());
        if (board != null) {
            board.setTitle(saveRequest.getTitle());
            board.setContent(saveRequest.getContent());
            board.setMember(member);
            board.setBoardType(boardType);
        } else {
            board = new Board();
            board.setTitle(saveRequest.getTitle());
            board.setContent(saveRequest.getContent());
            board.setMember(member);
            board.setBoardType(boardType);
        }
        boardRepository.save(board);
        log.info("게시글 저장 완료! 작성자: {}, 게시판: {}", userId, saveRequest.getBoardCode());
        return true;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        if (pos == -1) return "";
        return originalFilename.substring(pos + 1);
    }
}
