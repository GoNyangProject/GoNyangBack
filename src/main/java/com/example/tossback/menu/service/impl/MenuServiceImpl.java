package com.example.tossback.menu.service.impl;

import com.example.tossback.menu.dto.MenuResponseDTO;
import com.example.tossback.menu.entity.Menu;
import com.example.tossback.menu.repository.MenuRepository;
import com.example.tossback.menu.service.MenuService;
import com.example.tossback.mypage.book.dto.MypageBookResponseDTO;
import com.example.tossback.mypage.book.entity.Book;
import com.example.tossback.mypage.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
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
    public List<MypageBookResponseDTO> getBookByMonth(String date) {

        List<MypageBookResponseDTO> result = new ArrayList<>();

        YearMonth yearMonth = YearMonth.parse(date, DateTimeFormatter.ofPattern("yyyy-MM"));

        LocalDateTime startDate = yearMonth.atDay(1).atStartOfDay();
        LocalDateTime endDate = yearMonth.atEndOfMonth().atTime(23, 59, 59);

        List<Book> books = bookRepository.findBooksInMonth(startDate, endDate);

        for (Book book : books) {
            MypageBookResponseDTO bookResponseDTO = new MypageBookResponseDTO();
            bookResponseDTO.setOrderId(book.getOrderId());
            bookResponseDTO.setUsername(book.getMember().getUsername());
            bookResponseDTO.setMenuName(book.getMenu().getName());
            bookResponseDTO.setContent(book.getMenu().getContent());
            bookResponseDTO.setBookDate(book.getBookDate());
            bookResponseDTO.setPrice(book.getPrice());
            bookResponseDTO.setDeletedAt(book.getDeletedAt());
            result.add(bookResponseDTO);
        }

        return result;
    }
}
