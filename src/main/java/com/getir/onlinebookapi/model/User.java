package com.getir.onlinebookapi.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = -6751741329043415790L;

    private Integer id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private Boolean admin;
    private Boolean active;
}
