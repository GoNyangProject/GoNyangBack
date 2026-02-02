package com.example.tossback.board.boardLike.controller;

import com.example.tossback.board.boardLike.service.LikeService;
import com.example.tossback.board.dto.BoardRequestDTO;
import com.example.tossback.common.dto.CommonResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boardLike")
public class LikeController {

    private final LikeService likeService;

    @PostMapping
    public ResponseEntity<CommonResponse>toggleLike(@RequestBody BoardRequestDTO dto){
        return new ResponseEntity<>(new CommonResponse(likeService.toggleLike(dto)), HttpStatus.OK);
    }
}
