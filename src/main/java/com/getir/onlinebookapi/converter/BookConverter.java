package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.Book;
import com.getir.onlinebookapi.model.request.CreateBookRequest;
import org.springframework.stereotype.Component;

@Component
public class BookConverter {

    public Book convert(CreateBookRequest createBookRequest) {
        return Book.builder()
                .name(createBookRequest.getName())
                .author(createBookRequest.getAuthor())
                .price(createBookRequest.getPrice())
                .build();
    }
}
