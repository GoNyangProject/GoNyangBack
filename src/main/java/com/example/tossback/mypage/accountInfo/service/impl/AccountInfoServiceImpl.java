package com.example.tossback.mypage.accountInfo.service.impl;

import com.example.tossback.member.entity.Member;
import com.example.tossback.member.repository.MemberRepository;
import com.example.tossback.mypage.accountInfo.dto.*;
import com.example.tossback.mypage.accountInfo.entity.PetInfo;
import com.example.tossback.mypage.accountInfo.repository.PetInfoRepository;
import com.example.tossback.mypage.accountInfo.service.AccountInfoService;
import io.awspring.cloud.s3.S3Template;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class AccountInfoServiceImpl implements AccountInfoService {

    private final MemberRepository memberRepository;
    private final PetInfoRepository petInfoRepository;
    private final S3Template s3Template;

    public AccountInfoServiceImpl(MemberRepository memberRepository, PetInfoRepository petInfoRepository, S3Template s3Template) {
        this.memberRepository = memberRepository;
        this.petInfoRepository = petInfoRepository;
        this.s3Template = s3Template;
    }

    @Value("${spring.file.storage.path}")
    private String storagePath;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String S3_BUCKET;


    @Override
    public UserAndPetInfoResponse getUserAndPetInfo(String userId) {
        Member memberInfo = memberRepository.findByUserId(userId);
        if (memberInfo == null) {
            log.error("해당 회원이 존재하지 않습니다");
            throw new RuntimeException("해당 회원이 존재하지 않습니다.");
        }

        List<PetInfo> petsInfo = memberInfo.getPetInfoList();

        List<PetInfoResponse> petDTOs = petsInfo.stream().map(pet -> {
            PetInfoResponse dto = new PetInfoResponse();
            dto.setPetImagePath(pet.getPetImagePath());
            dto.setPetName(pet.getPetName());
            dto.setCatBreed(pet.getCatBreed());
            dto.setPetAge(pet.getPetAge() != null ? String.valueOf(pet.getPetAge()) : null);
            dto.setPetGender(pet.getPetGender() != null ? String.valueOf(pet.getPetGender()) : null);
            dto.setCatNotes(pet.getCatNotes());
            dto.setPetId(pet.getId());
            return dto;
        }).toList();

        UserAndPetInfoResponse userAndPetInfoResponse = new UserAndPetInfoResponse();
        userAndPetInfoResponse.setUsername(memberInfo.getUsername());
        userAndPetInfoResponse.setEmail(memberInfo.getEmail());
        userAndPetInfoResponse.setPhoneNumber(memberInfo.getPhoneNumber());
        userAndPetInfoResponse.setPets(petDTOs);

        return userAndPetInfoResponse;
    }

    @Transactional
    @Override
    public UserModifyResponse modifyProfile(UserProfileModify userProfileModify) {
        Member userId = memberRepository.findByUserId(userProfileModify.getUserId());
        if (userId == null) {
            throw new RuntimeException("해당 유저를 찾을 수 없습니다.");
        }
        switch (userProfileModify.getFieldType()) {
            case NAME:
                userId.setUsername(userProfileModify.getValue());
                break;
            case EMAIL:
                userId.setEmail(userProfileModify.getValue());
                break;
            case PHONE:
                userId.setPhoneNumber(userProfileModify.getValue());
                break;
            default:
                throw new IllegalArgumentException("잘못된 FieldType 값입니다.");
        }
        userId.setUpdatedAt(LocalDateTime.now());
        UserModifyResponse userModifyResponse = new UserModifyResponse();
        userModifyResponse.setStatus("ok");
        return userModifyResponse;
    }

    @Transactional
    @Override
    public MyPetProfileModifyResponse modifyPetProfile(MyPetProfileModify myPetProfileModify) {
        Member userId = memberRepository.findByUserId(myPetProfileModify.getUserId());
        if (userId == null) {
            throw new RuntimeException("해당 유저를 찾을 수 없습니다.");
        }

        PetInfo petInfo;
        if (myPetProfileModify.getPetId() != null) {
            petInfo = petInfoRepository.findById(myPetProfileModify.getPetId())
                    .orElseThrow(() -> new RuntimeException("해당 펫을 찾을 수 없습니다."));
        } else {
            petInfo = new PetInfo();
            petInfo.setMember(userId);
        }

        String imageBase64 = myPetProfileModify.getImageBase64();
        String finalPath = null;
        if (imageBase64 != null && !imageBase64.isBlank()) {
            if (imageBase64.startsWith("data:image")) {
                finalPath = saveImageToS3(imageBase64);
                petInfo.setPetImagePath(finalPath);
            } else if (imageBase64.startsWith("/")) {
                petInfo.setPetImagePath(imageBase64);
            } else {
                log.warn("예상하지 못한 imageBase64 형식입니다: {}", imageBase64);
            }
        } else {
            log.info("이미지가 제공되지 않았습니다. 기존 이미지를 유지합니다.");
        }


        petInfo.setPetName(myPetProfileModify.getName());
        petInfo.setCatBreed(myPetProfileModify.getBreed());
        petInfo.setPetAge(myPetProfileModify.getAge());
        petInfo.setPetGender(myPetProfileModify.getGender().charAt(0));
        petInfo.setCatNotes(myPetProfileModify.getNote());
        petInfo.setUpdatedAt(LocalDateTime.now());

        petInfoRepository.save(petInfo);

        MyPetProfileModifyResponse response = new MyPetProfileModifyResponse();
        response.setStatus("ok");
        response.setPetImagePath(finalPath);
        return response;
    }


    @Override
    @Transactional
    public PetDeleteResponse deleteProfilePet(PetDeleteRequest petDeleteRequest) {
        Member member = memberRepository.findByUserId(petDeleteRequest.getUserId());
        if (member == null) {
            throw new RuntimeException("해당 유저를 찾을 수 없습니다.");
        }

        PetInfo pet = petInfoRepository.findById(petDeleteRequest.getPetId())
                .orElseThrow(() -> new RuntimeException("해당 펫을 찾을 수 없습니다."));

        if (pet.getMember().getId() != member.getId()) {
            throw new RuntimeException("해당 유저의 펫이 아닙니다.");
        }
        petInfoRepository.delete(pet);

        PetDeleteResponse response = new PetDeleteResponse();
        response.setStatus(200);
        return response;
    }

    private String saveImageToS3(String base64Data) {
        String S3Folder = "mypage/";
        String[] parts = base64Data.split(",");
        String metadata = parts[0];
        String content = parts[1];

        String extension = metadata.split("/")[1].split(";")[0];
        byte[] decodedBytes = Base64.getDecoder().decode(content);

        String fileName = S3Folder + UUID.randomUUID() + "." + extension;

        try (InputStream inputStream = new ByteArrayInputStream(decodedBytes)) {
            var resource = s3Template.upload(S3_BUCKET, fileName, inputStream);
            return resource.getURL().toString();
        } catch (IOException e) {
            throw new RuntimeException("S3 업로드 중 에러 발생", e);
        }
    }
}
