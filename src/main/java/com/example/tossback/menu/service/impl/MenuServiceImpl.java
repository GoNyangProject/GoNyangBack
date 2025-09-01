package com.example.tossback.menu.service.impl;

import com.example.tossback.menu.dto.MenuResponseDTO;
import com.example.tossback.menu.entity.Menu;
import com.example.tossback.menu.repository.MenuRepository;
import com.example.tossback.menu.service.MenuService;
import com.example.tossback.mypage.book.dto.BookResponseDTO;
import com.example.tossback.mypage.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;
    private final BookRepository bookRepository;

    public MenuServiceImpl(MenuRepository menuRepository, BookRepository bookRepository) {
        this.menuRepository = menuRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<MenuResponseDTO> getMenuList() {
        List<MenuResponseDTO> result = new ArrayList<>();

        List<Menu> menus = menuRepository.findAll();
        for (Menu menu : menus) {
            MenuResponseDTO menuDTO = new MenuResponseDTO();
            menuDTO.setId(menu.getId());
            menuDTO.setMenuName(menu.getName());
//            BigDecimal score = menu.getScore().setScale(2, RoundingMode.HALF_UP);
            menuDTO.setScore(menu.getScore());
            menuDTO.setContent(menu.getContent());
            menuDTO.setPrice(menu.getPrice());
            menuDTO.setBookCount(menu.getBookCount());
            result.add(menuDTO);
        }
        return result;
    }

    @Override
    public List<BookResponseDTO> getBookByMonth(String date) {
        System.out.println("date = " + date);
        return List.of();
    }
}
