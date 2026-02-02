package com.example.tossback.admin.community.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public  class CommunityInfo {
    private long id;
    private String boardName;
    private String title;
    private String userId;
    private int viewCount;
    private int likeCount;
    private String createdAt;
    private String deletedAt;
}
