package com.example.tossback.board.controller;

import com.example.tossback.board.dto.BoardRequestDTO;
import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.board.service.BoardService;
import com.example.tossback.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping()
    public ResponseEntity<CommonResponse> getBoard(@RequestParam BoardCode boardCode, @RequestParam String searchKeyword, @RequestParam int size, @RequestParam int page) {
        return new ResponseEntity<>(new CommonResponse(boardService.getBoards(boardCode, searchKeyword, size, page)), HttpStatus.OK);
    }

    @GetMapping("/detail")
    public ResponseEntity<CommonResponse> getBoardDetail(@RequestParam long boardCode, @RequestParam String userId) {
        return new ResponseEntity<>(new CommonResponse(boardService.getBoardDetail(boardCode, userId)), HttpStatus.OK);
    }

    @PostMapping("/like")
    public ResponseEntity<CommonResponse> updateLike(@RequestBody BoardRequestDTO boardRequestDTO) {
        return new ResponseEntity<>(new CommonResponse(boardService.updateLike(boardRequestDTO.getBoardId())), HttpStatus.OK);
    }

}
