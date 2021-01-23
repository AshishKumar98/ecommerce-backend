package com.ecom.userservice.rest.model;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserData {

    private String name;
    private int age;
    private String email;
    private String password;
}