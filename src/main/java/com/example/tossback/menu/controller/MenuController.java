package com.example.tossback.menu.controller;

import com.example.tossback.common.dto.CommonResponse;
import com.example.tossback.menu.service.MenuService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {

    private final MenuService menuService;

    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @GetMapping()
    public ResponseEntity<CommonResponse> getMenu() {
        return new ResponseEntity<>(new CommonResponse(menuService.getMenuList()), HttpStatus.OK);
    }

    @GetMapping("/month")
    public ResponseEntity<CommonResponse> getBookByMonth(@RequestParam("date") String date) {
        return new ResponseEntity<>(new CommonResponse(menuService.getBookByMonth(date)), HttpStatus.OK);
    }

}
