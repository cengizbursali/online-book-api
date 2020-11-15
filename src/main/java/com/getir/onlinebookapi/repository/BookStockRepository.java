package com.getir.onlinebookapi.repository;

import com.getir.onlinebookapi.entity.BookStock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface BookStockRepository extends JpaRepository<BookStock, Integer> {

    @Modifying
    @Query("update BookStock set quantity = quantity - :qty where bookId = :bookId and quantity >= :qty")
    int decrementBookStock(Integer bookId, Integer qty);
}
