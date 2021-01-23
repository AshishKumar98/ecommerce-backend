package com.ecom.userservice.rest.controller;

import com.ecom.userservice.entity.Cart;
import com.ecom.userservice.entity.User;
import com.ecom.userservice.mapping.JSONMapper;
import com.ecom.userservice.rest.model.CartData;
import com.ecom.userservice.rest.model.UserLogin;
import com.ecom.userservice.service.CartService;
import com.ecom.userservice.service.UserService;
import com.sun.org.apache.xpath.internal.operations.Bool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartRestController {

    @Autowired
    UserService userService;

    @Autowired
    CartService cartService;

    private static Map<String, Boolean> map = new ConcurrentHashMap<>();

    @PostMapping
    public ResponseEntity<Boolean> addToCart(@RequestBody CartData cart) {
        log.info("add cart: {}", cart);
        User user = userService.getUserByEmail(cart.getEmail());
        log.info("user for cart add: {}", user);

        for (String email: map.keySet()){
            log.info("MAP: {} -> {}\n",email, map.get(email) );
        }

        if (map.size()>0 && map.containsKey(cart.getEmail()) &&
                map.get(cart.getEmail()) == true) {
            log.info("User authenticated success");
            Cart oldCart = cartService.getCartByUserID(user.getUserID());
            log.info("OLD CArt: {}", oldCart);
            Cart cartItem = JSONMapper.cartdataToEntity(cart, user.getUserID());
            if(null == oldCart) {
                log.info("New Cart {}", cartItem);
                cartService.saveToCart(cartItem);
            } else {
                oldCart.setItemIDs(cart.getItemIDs());
                log.info("update Cart {}", oldCart);
                if (cartService.updateCart(oldCart)==null) {
                    return ResponseEntity.ok(false);
                }
            }
            return ResponseEntity.ok(true);
        } else {
            log.info("User Not authenticated");
        }
        return ResponseEntity.ok(false);
    }

    @GetMapping("/authenticate")
    public ResponseEntity<Boolean> authenticateUser(final UserLogin loginData){
        User user = userService.getUserByEmail(loginData.getEmail());
        if(null != user && user.getPassword().equals(loginData.getPassword())){
            map.put(loginData.getEmail(), true);
            for (String email: map.keySet()){
                log.info("MAP: {} -> {}\n",email, map.get(email) );
            }
            log.info("authenticated success:{} , Is logedin: {}",
                    loginData.getEmail(), map.get(loginData.getEmail()));
            return ResponseEntity.ok(true);
        }
        for (String email: map.keySet()){
            log.info("MAP: {} -> {}\n",email, map.get(email) );
        }

        log.error("authenticated failed :{} , Is logedin: {}",
                    loginData.getEmail(), map.containsKey(loginData.getEmail())
                                                            ?map.get(loginData.getEmail())
                                                            :"Never Logged");
        return ResponseEntity.ok(false);
    }

    @GetMapping("/logout")
    public ResponseEntity<Boolean> authenticateUser(final String email){
        log.info("Logout Request for: {}", email);
        if(null != email && !"".equals(email) && map.containsKey(email)) {
            map.put(email, false);
        }
        return ResponseEntity.ok(true);
    }




}
