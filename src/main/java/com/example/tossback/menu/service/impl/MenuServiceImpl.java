package com.example.tossback.menu.service.impl;

import com.example.tossback.menu.dto.MenuResponseDTO;
import com.example.tossback.menu.entity.Menu;
import com.example.tossback.menu.repository.MenuRepository;
import com.example.tossback.menu.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    private final MenuRepository menuRepository;

    public MenuServiceImpl(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
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
}
