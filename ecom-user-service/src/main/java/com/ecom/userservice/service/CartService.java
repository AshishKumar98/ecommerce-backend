package com.ecom.userservice.service;

import com.ecom.userservice.entity.Cart;
import com.ecom.userservice.entity.User;

public interface CartService {

    Cart saveToCart(Cart cart);
    Cart getCartByUserID(Long userID);
    //User getUser(User user);
    //boolean authenticateUser(final String email, final String password);
    Cart getCartByUserID(String email);

    Cart updateCart(Cart cart);

}
