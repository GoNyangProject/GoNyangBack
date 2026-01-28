package com.example.tossback.admin.block.servcie.impl;

import com.example.tossback.admin.block.dto.AdminBlockRequest;
import com.example.tossback.admin.block.dto.AdminBlockResponse;
import com.example.tossback.admin.block.entity.BlockDate;
import com.example.tossback.admin.block.repository.BlockDateRepository;
import com.example.tossback.admin.block.servcie.AdminBlockService;
import com.example.tossback.common.enums.ErrorCode;
import com.example.tossback.common.exception.CommonException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminBlockServiceImpl implements AdminBlockService {

    private final BlockDateRepository blockDateRepository;

    @Transactional
    @Override
    public Boolean blockDate(AdminBlockRequest request) {
        log.info("AdminBlock 특정 날짜 차단 시도: {}", request.getBlockDate());

        if (blockDateRepository.existsByBlockDate(request.getBlockDate())) {
            log.warn("[AdminBlock] 중복 차단 요청 거부: {}", request.getBlockDate());
            throw new CommonException("해당 날짜는 이미 차단되어 있습니다.", ErrorCode.INVALID_PARAMETER);
        }
        try {
            BlockDate blockDate = BlockDate.builder()
                    .blockDate(request.getBlockDate())
                    .reason(request.getReason() != null ? request.getReason() : "관리자 차단")
                    .build();

            blockDateRepository.save(blockDate);
            log.info("[AdminBlock] 날짜 차단 완료: {}", request.getBlockDate());
            return true;

        } catch (Exception e) {
            log.error("[AdminBlock] 날짜 차단 중 서버 에러 발생: {}", e.getMessage());
            throw new CommonException("서버 오류로 인해 차단에 실패했습니다.", ErrorCode.UNKNOWN);
        }
    }

    @Override
    public List<AdminBlockResponse> getBlockList() {
        return blockDateRepository.findAll().stream()
                .map(blockDate -> AdminBlockResponse.builder()
                        .id(blockDate.getId())
                        .blockDate(blockDate.getBlockDate())
                        .reason(blockDate.getReason())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Boolean unblockDate(String date) {
        LocalDate localDate = LocalDate.parse(date);

        BlockDate blockDate = blockDateRepository.findByBlockDate(localDate)
                .orElseThrow(() -> new CommonException("차단되지 않은 날짜입니다.", ErrorCode.INVALID_PARAMETER));
        try {
            blockDateRepository.delete(blockDate);
            log.info("[AdminBlock] 차단 해제 완료: {}", date);
            return true;
        } catch (Exception e) {
            log.error("[AdminBlock] 차단 해제 중 에러 발생: {}", e.getMessage());
            throw new CommonException("차단 해제 처리 중 오류가 발생했습니다.", ErrorCode.UNKNOWN);
        }
    }
}
