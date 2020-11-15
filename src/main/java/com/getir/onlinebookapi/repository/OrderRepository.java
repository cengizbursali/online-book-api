package com.getir.onlinebookapi.repository;

import com.getir.onlinebookapi.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByCustomerId(Integer customerId);
}
