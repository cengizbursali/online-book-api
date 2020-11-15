package com.getir.onlinebookapi.service;

import com.getir.onlinebookapi.converter.BookStockDtoConverter;
import com.getir.onlinebookapi.entity.BookStock;
import com.getir.onlinebookapi.exception.OnlineBookApiException;
import com.getir.onlinebookapi.model.response.BookStockDto;
import com.getir.onlinebookapi.repository.BookStockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookStockService {

    private final BookStockRepository bookStockRepository;
    private final BookStockDtoConverter bookStockDtoConverter;

    public void decrementStock(Map<Integer, Integer> bookMap) {
        bookMap.forEach((bookId, quantity) -> {
            int affectedRow = bookStockRepository.decrementBookStock(bookId, quantity);
            if (affectedRow == 0) {
                throw new OnlineBookApiException("book.out.of.stock", HttpStatus.BAD_REQUEST, String.valueOf(bookId));
            }
        });
    }

    public void create(Integer bookId, Integer stockQuantity) {
        final BookStock bookStock = BookStock.builder()
                .bookId(bookId)
                .quantity(stockQuantity)
                .build();
        bookStockRepository.save(bookStock);
        log.info("BookStock saved to database: {}", bookStock);
    }

    public List<BookStockDto> getAll() {
        final List<BookStock> bookStockList = bookStockRepository.findAll();
        return bookStockList.stream().map(bookStockDtoConverter::convert).collect(Collectors.toList());
    }
}
