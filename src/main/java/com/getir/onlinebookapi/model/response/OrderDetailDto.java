package com.getir.onlinebookapi.model.response;

import com.getir.onlinebookapi.entity.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderDetailDto {

    private Integer id;
    private BookDto bookDto;
    private Integer orderId;
    private Integer quantity;
    private BigDecimal price;
    private OrderStatus orderStatus;
}
