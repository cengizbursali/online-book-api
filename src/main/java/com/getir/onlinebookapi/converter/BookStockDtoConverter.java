package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.BookStock;
import com.getir.onlinebookapi.model.response.BookStockDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BookStockDtoConverter {

    private final BookDtoConverter bookDtoConverter;

    public BookStockDto convert(BookStock bookStock) {
        return BookStockDto.builder()
                .stockQuantity(bookStock.getQuantity())
                .bookDto(bookDtoConverter.convert(bookStock.getBook()))
                .build();
    }
}
