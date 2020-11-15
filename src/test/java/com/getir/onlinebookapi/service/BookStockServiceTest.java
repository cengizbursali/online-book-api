package com.getir.onlinebookapi.service;

import com.getir.onlinebookapi.converter.BookStockDtoConverter;
import com.getir.onlinebookapi.entity.BookStock;
import com.getir.onlinebookapi.exception.OnlineBookApiException;
import com.getir.onlinebookapi.model.response.BookStockDto;
import com.getir.onlinebookapi.repository.BookStockRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BookStockServiceTest {

    @InjectMocks
    private BookStockService bookStockService;

    @Mock
    private BookStockRepository bookStockRepository;

    @Mock
    private BookStockDtoConverter bookStockDtoConverter;

    @Test
    public void it_should_decrement_stock() {
        // Given
        HashMap<Integer, Integer> bookMap = new HashMap<>();
        bookMap.put(11223, 1);
        bookMap.put(11224, 2);
        when(bookStockRepository.decrementBookStock(11223, 1)).thenReturn(1);
        when(bookStockRepository.decrementBookStock(11224, 2)).thenReturn(1);

        // When
        bookStockService.decrementStock(bookMap);

        // Then
        verify(bookStockRepository).decrementBookStock(11223, 1);
        verify(bookStockRepository).decrementBookStock(11224, 2);
    }

    @Test
    public void it_should_throw_exception_when_book_out_of_stock() {
        // Given
        HashMap<Integer, Integer> bookMap = new HashMap<>();
        bookMap.put(11223, 1);
        bookMap.put(11224, 2);
        when(bookStockRepository.decrementBookStock(11223, 1)).thenReturn(0);

        // When
        Throwable throwable = catchThrowable(() -> bookStockService.decrementStock(bookMap));

        // Then
        assertThat(throwable).isInstanceOf(OnlineBookApiException.class);
        OnlineBookApiException exception = (OnlineBookApiException) throwable;
        assertThat(exception.getKey()).isEqualTo("book.out.of.stock");
        assertThat(exception.getHttpStatus()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(exception.getArgs()).containsExactly("11223");
        verify(bookStockRepository).decrementBookStock(11223, 1);
        verifyNoMoreInteractions(bookStockRepository);
    }

    @Test
    public void it_should_create() {
        // When
        bookStockService.create(1, 100);

        // Then
        ArgumentCaptor<BookStock> bookStockArgumentCaptor = ArgumentCaptor.forClass(BookStock.class);
        verify(bookStockRepository).save(bookStockArgumentCaptor.capture());
        assertThat(bookStockArgumentCaptor.getValue().getBookId()).isEqualTo(1);
        assertThat(bookStockArgumentCaptor.getValue().getQuantity()).isEqualTo(100);
    }

    @Test
    public void it_should_get_all_book_stock_quantity() {
        // Given
        BookStock bookStock = BookStock.builder().build();
        when(bookStockRepository.findAll()).thenReturn(List.of(bookStock));
        BookStockDto bookStockDto = BookStockDto.builder().build();
        when(bookStockDtoConverter.convert(bookStock)).thenReturn(bookStockDto);

        // When
        List<BookStockDto> bookStockDtoList = bookStockService.getAll();

        // Then
        assertThat(bookStockDtoList).containsExactly(bookStockDto);
        verify(bookStockRepository).findAll();
        verify(bookStockDtoConverter).convert(bookStock);
    }
}