package com.getir.onlinebookapi.repository;

import com.getir.onlinebookapi.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Integer> {

}
