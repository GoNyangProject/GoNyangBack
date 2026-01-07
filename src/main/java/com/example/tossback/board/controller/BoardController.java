package com.example.tossback.board.controller;

import com.example.tossback.board.enums.BoardCode;
import com.example.tossback.board.service.BoardService;
import com.example.tossback.common.dto.CommonResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<CommonResponse> getBoardDetail(@RequestParam long boardCode) {
        return new ResponseEntity<>(new CommonResponse(boardService.getBoardDetail(boardCode)), HttpStatus.OK);
    }

}
