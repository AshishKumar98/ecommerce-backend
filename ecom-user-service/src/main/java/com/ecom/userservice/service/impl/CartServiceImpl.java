package com.ecom.userservice.service.impl;

import com.ecom.userservice.entity.Cart;
import com.ecom.userservice.repository.CartRepository;
import com.ecom.userservice.service.CartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;


    @Override
    public Cart saveToCart(Cart cart) {
        return cartRepository.save(cart);
    }

    @Override
    public Cart getCartByUserID(Long userID) {
        log.info("Get By ID at Service: {}", userID);
        Cart cart = cartRepository.findBYUserID(userID);
        log.info("Get Cart Service: {}", cart);
        return cart;
    }


    @Override
    public Cart getCartByUserID(String email) {
        return null;
    }

    @Override
    public Cart updateCart(Cart cart) {
        log.info("Update cart:{}", cart);
        Cart existedCart = cartRepository.findBYUserID(cart.getUserID());
        log.info("existed cart:{}", existedCart);
        if(existedCart != null) {
            existedCart.setCartID(cart.getCartID());
            existedCart.setItemIDs(cart.getItemIDs());
            Cart updatedCart = cartRepository.save(existedCart);
            log.info("Cart Updated with: {}", updatedCart);
            return updatedCart;
        }
        log.error("Cart Update Failed {}", cart);

        return null;
    }
}
