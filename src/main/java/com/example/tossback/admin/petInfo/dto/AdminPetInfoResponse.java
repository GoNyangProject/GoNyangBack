package com.example.tossback.admin.petInfo.dto;

import com.example.tossback.mypage.accountInfo.entity.PetInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AdminPetInfoResponse {
    private Long id;
    private String petName;
    private String catBreed;
    private Integer petAge;
    private String petGender;
    private String catNotes;
    private String petImagePath;
    private LocalDateTime createdAt;

    public static AdminPetInfoResponse fromEntity(PetInfo pet) {
        return AdminPetInfoResponse.builder()
                .id(pet.getId())
                .petName(pet.getPetName())
                .catBreed(pet.getCatBreed())
                .petAge(pet.getPetAge())
                .petGender(String.valueOf(pet.getPetGender()))
                .catNotes(pet.getCatNotes())
                .petImagePath(pet.getPetImagePath())
                .createdAt(pet.getCreatedAt())
                .build();
    }
}