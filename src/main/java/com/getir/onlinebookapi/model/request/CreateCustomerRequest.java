package com.getir.onlinebookapi.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CreateCustomerRequest {

    @NotBlank(message = "createCustomerRequest.name.notBlank")
    private String name;

    @NotBlank(message = "createCustomerRequest.surname.notBlank")
    private String surname;

    @NotNull(message = "createCustomerRequest.age.notNull")
    @Min(value = 18, message = "createCustomerRequest.age.min")
    private Integer age;

    @NotBlank(message = "createCustomerRequest.email.notBlank")
    @Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "createCustomerRequest.email.format")
    private String email;
}
