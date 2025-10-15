package com.example.tossback.mypage.accountInfo.service;

import com.example.tossback.mypage.accountInfo.dto.*;

public interface AccountInfoService {
    UserAndPetInfoResponse getUserAndPetInfo(String userId);

    UserModifyResponse modifyProfile(UserProfileModify userProfileModify);

    MyPetProfileModifyResponse modifyPetProfile(MyPetProfileModify myPetProfileModify);

    PetDeleteResponse deleteProfilePet(PetDeleteRequest petDeleteRequest);
}
