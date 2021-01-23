package com.ecom.userservice.rest.model;

import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class UserLogin {
    private String email;
    private String password;
}
