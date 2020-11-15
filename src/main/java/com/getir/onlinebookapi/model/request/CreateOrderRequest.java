package com.getir.onlinebookapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Map;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateOrderRequest {

    @NotNull(message = "createOrderRequest.customerId.notNull")
    private Integer customerId;

    @NotEmpty(message = "createOrderRequest.bookMap.notEmpty")
    private Map<Integer, Integer> bookMap;
}
