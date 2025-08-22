package com.example.tossback.mypage.accountInfo.controller;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.mypage.accountInfo.dto.MyPetProfileModify;
import com.example.tossback.mypage.accountInfo.dto.PetDeleteRequest;
import com.example.tossback.mypage.accountInfo.dto.UserProfileModify;
import com.example.tossback.mypage.accountInfo.service.AccountInfoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mypage/useraccount")
public class AccountInfoController {

    private final AccountInfoService accountInfoService;

    public AccountInfoController(AccountInfoService accountInfoService) {
        this.accountInfoService = accountInfoService;
    }

    @GetMapping
    public ResponseEntity<CommonResponse> getUserAndPetAccount(@RequestParam String userId) {
        return new ResponseEntity<>(new CommonResponse(accountInfoService.getUserAndPetInfo(userId)), HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<CommonResponse> modifyProfile(@RequestBody UserProfileModify userProfileModify) {
        return new ResponseEntity<>(new CommonResponse(accountInfoService.modifyProfile(userProfileModify)), HttpStatus.OK);
    }

    @PostMapping("/profile/pet")
    public ResponseEntity<CommonResponse> modifyProfilePet(@RequestBody MyPetProfileModify myPetProfileModify){
        return new ResponseEntity<>(new CommonResponse(accountInfoService.modifyPetProfile(myPetProfileModify)), HttpStatus.OK);
    }
    @PostMapping("/profile/pet/delete")
    public ResponseEntity<CommonResponse> deleteProfilePet(@RequestBody PetDeleteRequest petDeleteRequest){
        return new ResponseEntity<>(new CommonResponse(accountInfoService.deleteProfilePet(petDeleteRequest)), HttpStatus.OK);
    }
}
