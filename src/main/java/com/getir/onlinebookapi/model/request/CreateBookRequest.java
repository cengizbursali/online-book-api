package com.getir.onlinebookapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateBookRequest {

    @NotBlank(message = "createBookRequest.name.notBlank")
    private String name;

    @NotBlank(message = "createBookRequest.author.notBlank")
    private String author;

    @NotNull(message = "createBookRequest.price.notNull")
    private BigDecimal price;

    @Builder.Default
    @Min(value = 1, message = "createBookRequest.stockQuantity.min")
    private Integer stockQuantity = 1;
}
