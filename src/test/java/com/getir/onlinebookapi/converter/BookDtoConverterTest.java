package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.Book;
import com.getir.onlinebookapi.model.response.BookDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BookDtoConverterTest {

    @InjectMocks
    private BookDtoConverter bookDtoConverter;

    @Test
    public void it_should_convert() {
        // Given
        Book book = Book.builder()
                .price(BigDecimal.TEN)
                .name("Clean Code")
                .author("Robert Cecil Martin")
                .id(101)
                .build();

        // When
        BookDto bookDto = bookDtoConverter.convert(book);

        // Then
        assertThat(bookDto.getPrice()).isEqualTo(BigDecimal.TEN);
        assertThat(bookDto.getName()).isEqualTo("Clean Code");
        assertThat(bookDto.getAuthor()).isEqualTo("Robert Cecil Martin");
        assertThat(bookDto.getId()).isEqualTo(101);
    }
}