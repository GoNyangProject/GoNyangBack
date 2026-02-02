package com.example.tossback.board.comment.dto;

import com.example.tossback.board.comment.entity.Comment;
import com.example.tossback.mypage.accountInfo.entity.PetInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class CommentResponseDTO {

    private long id;
    private String content;
    private LocalDateTime createdAt;
    private LocalDateTime deletedAt;
    List<CommentResponseDTO> children;
    private String writer;
    private String petImagePath;

//    public static CommentResponseDTO from(Comment comment) {
//        CommentResponseDTO dto = new CommentResponseDTO();
//        dto.setId(comment.getId());
//        dto.setContent(comment.getContent());
//        dto.setWriter(comment.getMember().getUsername());
//        dto.setCreatedAt(comment.getCreatedAt());
//        if (comment.getMember() != null && comment.getMember().getPetInfoList() != null) {
//            String path = comment.getMember().getPetInfoList().stream()
//                    .filter(pet -> pet != null && pet.getDeletedAt() == null)
//                    .map(pet -> pet.getPetImagePath())
//                    .filter(java.util.Objects::nonNull)
//                    .findFirst()
//                    .orElse(null);
//            dto.setPetImagePath(path);
//        }
//
//        dto.setChildren(comment.getChildren().stream()
//                .map(CommentResponseDTO::from)
//                .collect(Collectors.toList()));
//
//        return dto;
//    }
public static CommentResponseDTO from(Comment comment) {
    CommentResponseDTO dto = new CommentResponseDTO();
    dto.setId(comment.getId());
    dto.setContent(comment.getContent());

    // 작성자 정보 안전하게 가져오기
    if (comment.getMember() != null) {
        dto.setWriter(comment.getMember().getUsername());

        // 🐾 펫 이미지 리스트가 로딩될 때 에러 안 나게 체크
        if (comment.getMember().getPetInfoList() != null) {
            String path = comment.getMember().getPetInfoList().stream()
                    .filter(pet -> pet != null && pet.getDeletedAt() == null)
                    .map(PetInfo::getPetImagePath)
                    .filter(java.util.Objects::nonNull)
                    .findFirst()
                    .orElse(null);
            dto.setPetImagePath(path);
        }
    }

    dto.setCreatedAt(comment.getCreatedAt());

    if (comment.getChildren() != null) {
        dto.setChildren(comment.getChildren().stream()
                .map(CommentResponseDTO::from)
                .collect(Collectors.toList()));
    }

    return dto;
}

}
