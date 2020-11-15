package com.getir.onlinebookapi.converter;

import com.getir.onlinebookapi.entity.Book;
import com.getir.onlinebookapi.model.request.CreateBookRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class BookConverterTest {

    @InjectMocks
    private BookConverter bookConverter;

    @Test
    public void it_should_convert() {
        // Given
        CreateBookRequest createBookRequest = CreateBookRequest.builder()
                .name("sefiller")
                .author("victor hugo")
                .price(BigDecimal.TEN)
                .build();

        // When
        Book book = bookConverter.convert(createBookRequest);

        // Then
        assertThat(book.getName()).isEqualTo("sefiller");
        assertThat(book.getAuthor()).isEqualTo("victor hugo");
        assertThat(book.getPrice()).isEqualTo(BigDecimal.TEN);
    }
}