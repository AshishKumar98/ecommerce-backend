package com.ecom.userservice.mapping;

import com.ecom.userservice.entity.Cart;
import com.ecom.userservice.entity.User;
import com.ecom.userservice.rest.model.CartData;
import com.ecom.userservice.rest.model.UserData;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class JSONMapper {

    public static UserData entityToUserData(User user){
        return UserData.builder()
                .name(user.getName())
                .age(user.getAge())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static User userDataToEntity(UserData user){
        return User.builder()
                .name(user.getName().toLowerCase())
                .age(user.getAge())
                .email(user.getEmail().toLowerCase())
                .password(user.getPassword())
                .build();
    }

    public static List<UserData> entityToUserData(List<User> userList) {
        return userList.stream()
                .map(JSONMapper::entityToUserData)
                .collect(Collectors.toList());
    }

    public static List<User> userDataToEntity(List<UserData> userList) {
        return userList.stream()
                .map(JSONMapper::userDataToEntity)
                .collect(Collectors.toList());
    }


    public static Cart cartdataToEntity(CartData cart, Long userId){
        return Cart.builder()
                .userID(userId)
                .itemIDs(cart.getItemIDs())
                .build();
    }

/*    public static Cart userDataToEntity(Cart cart){
        return Cart.builder()
                .name(user.getName().toLowerCase())
                .age(user.getAge())
                .email(user.getEmail().toLowerCase())
                .password(user.getPassword())
                .build();
    }*/



}
