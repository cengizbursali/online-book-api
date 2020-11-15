package com.getir.onlinebookapi.repository;

import com.getir.onlinebookapi.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> findAllByOrderId(Integer orderId);
}
