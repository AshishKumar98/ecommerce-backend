package com.ecom.userservice.entity;


import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "usercart")
@Builder
public class Cart {

    @Id
    @GeneratedValue
    private Long cartID;

    @Column(name="userid", nullable = false, unique = true)
    private Long userID;

    @Column(name="itemids")
    private String itemIDs;

}
