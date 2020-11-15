package com.getir.onlinebookapi.service;

import com.getir.onlinebookapi.converter.BookConverter;
import com.getir.onlinebookapi.converter.BookDtoConverter;
import com.getir.onlinebookapi.entity.Book;
import com.getir.onlinebookapi.exception.OnlineBookApiException;
import com.getir.onlinebookapi.model.request.CreateBookRequest;
import com.getir.onlinebookapi.model.response.BookDto;
import com.getir.onlinebookapi.repository.BookRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepository bookRepository;

    @Mock
    private BookConverter bookConverter;

    @Mock
    private BookDtoConverter bookDtoConverter;

    @Mock
    private BookStockService bookStockService;

    @Test
    public void it_should_create_book() {
        // Given
        CreateBookRequest createBookRequest = CreateBookRequest.builder().stockQuantity(100).build();
        Book book = Book.builder().id(1).build();
        when(bookConverter.convert(createBookRequest)).thenReturn(book);

        // When
        bookService.create(createBookRequest);

        // Then
        verify(bookConverter).convert(createBookRequest);
        verify(bookRepository).save(book);
        verify(bookStockService).create(1, 100);
    }

    @Test
    public void it_should_get_book_by_id() {
        // Given
        Book book = Book.builder().build();
        when(bookRepository.findById(33)).thenReturn(Optional.of(book));
        BookDto bookDto = BookDto.builder().build();
        when(bookDtoConverter.convert(book)).thenReturn(bookDto);

        // When
        BookDto result = bookService.getById(33);

        // Then
        verify(bookRepository).findById(33);
        verify(bookDtoConverter).convert(book);
        assertThat(result).isEqualTo(bookDto);
    }

    @Test
    public void it_should_throw_exception_when_book_not_found_by_id() {
        // Given
        when(bookRepository.findById(33)).thenReturn(Optional.empty());

        // When
        Throwable throwable = catchThrowable(() -> bookService.getById(33));

        // Then
        assertThat(throwable).isInstanceOf(OnlineBookApiException.class);
        OnlineBookApiException exception = (OnlineBookApiException) throwable;
        assertThat(exception.getKey()).isEqualTo("book.not.found");
        assertThat(exception.getHttpStatus()).isEqualTo(HttpStatus.NOT_FOUND);
        assertThat(exception.getArgs()).containsExactly("33");
        verify(bookRepository).findById(33);
        verifyNoInteractions(bookDtoConverter);
    }
}