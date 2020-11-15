package com.getir.onlinebookapi.controller;

import com.getir.onlinebookapi.model.request.CreateBookRequest;
import com.getir.onlinebookapi.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    @PostMapping
    @ResponseStatus(CREATED)
    public void create(@RequestBody @Valid CreateBookRequest createBookRequest) {
        log.info("createBookRequest: {}", createBookRequest);
        bookService.create(createBookRequest);
    }
}
