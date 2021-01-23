package com.ecom.userservice.entity;

import lombok.*;

import javax.persistence.*;


@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Setter
@Entity
@Table(name = "userdetails")
@Builder
@NamedQuery(name="query_get_all_books", query = "select u from User u")
public class User {

    @Id
    @GeneratedValue
    private Long userID;

    @Column(name="name", nullable = false, unique = true)
    private String name;

    @Column(name="age")
    private int age;

    @Column(name="email", nullable = false, unique = true)
    private String email;

    @Column(name="password", nullable = false)
    private String password;

}
