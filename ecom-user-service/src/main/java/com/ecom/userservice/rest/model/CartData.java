package com.ecom.userservice.rest.model;

import lombok.*;

@ToString
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CartData {
    private String itemIDs;
    private String email;
}
