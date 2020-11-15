package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.Book;
import com.getir.onlinebookapi.entity.BookStock;
import com.getir.onlinebookapi.model.response.BookDto;
import com.getir.onlinebookapi.model.response.BookStockDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookStockDtoConverterTest {

    @InjectMocks
    private BookStockDtoConverter bookStockDtoConverter;

    @Mock
    private BookDtoConverter bookDtoConverter;

    @Test
    public void it_should_convert() {
        // Given
        Book book = Book.builder().build();
        BookStock bookStock = BookStock.builder()
                .quantity(7)
                .book(book)
                .build();
        BookDto bookDto = BookDto.builder().build();
        when(bookDtoConverter.convert(book)).thenReturn(bookDto);

        // When
        BookStockDto bookStockDto = bookStockDtoConverter.convert(bookStock);

        // Then
        assertThat(bookStockDto.getBookDto()).isEqualTo(bookDto);
        assertThat(bookStockDto.getStockQuantity()).isEqualTo(7);
    }
}