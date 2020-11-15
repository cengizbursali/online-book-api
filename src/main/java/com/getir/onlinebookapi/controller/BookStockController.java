package com.getir.onlinebookapi.controller;

import com.getir.onlinebookapi.model.response.BookStockDto;
import com.getir.onlinebookapi.service.BookStockService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/book-stocks")
public class BookStockController {

    private final BookStockService bookStockService;

    @GetMapping
    public List<BookStockDto> getAll() {
        return bookStockService.getAll();
    }
}
