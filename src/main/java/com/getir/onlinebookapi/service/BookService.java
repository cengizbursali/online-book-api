package com.getir.onlinebookapi.service;

import com.getir.onlinebookapi.converter.BookConverter;
import com.getir.onlinebookapi.converter.BookDtoConverter;
import com.getir.onlinebookapi.entity.Book;
import com.getir.onlinebookapi.exception.OnlineBookApiException;
import com.getir.onlinebookapi.model.request.CreateBookRequest;
import com.getir.onlinebookapi.model.response.BookDto;
import com.getir.onlinebookapi.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookService {

    public static final String BOOK_NOT_FOUND = "book.not.found";

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;
    private final BookStockService bookStockService;
    private final BookDtoConverter bookDtoConverter;

    @Transactional
    public void create(CreateBookRequest createBookRequest) {
        final Book book = bookConverter.convert(createBookRequest);
        bookRepository.save(book);
        bookStockService.create(book.getId(), createBookRequest.getStockQuantity());
        log.info("Book saved to database: {}", book);
    }

    public BookDto getById(Integer id) {
        return bookRepository.findById(id)
                .map(bookDtoConverter::convert)
                .orElseThrow(() -> new OnlineBookApiException(BOOK_NOT_FOUND, HttpStatus.NOT_FOUND, String.valueOf(id)));
    }
}
