package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.Book;
import com.getir.onlinebookapi.model.response.BookDto;
import org.springframework.stereotype.Component;

@Component
public class BookDtoConverter {

    public BookDto convert(Book book) {
        return BookDto.builder()
                .id(book.getId())
                .price(book.getPrice())
                .author(book.getAuthor())
                .name(book.getName())
                .build();
    }
}
