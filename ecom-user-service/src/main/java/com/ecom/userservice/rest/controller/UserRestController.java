package com.ecom.userservice.rest.controller;

import com.ecom.userservice.entity.User;
import com.ecom.userservice.mapping.JSONMapper;
import com.ecom.userservice.rest.model.UserData;
import com.ecom.userservice.rest.model.UserLogin;
import com.ecom.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/user")
public class UserRestController {

    private Map<String, Boolean> map = new ConcurrentHashMap<>();

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<UserData> createUser(@RequestBody UserData userData) {
        User user = userService.saveUser(JSONMapper.userDataToEntity(userData));
        return ResponseEntity.ok(JSONMapper.entityToUserData(user));
    }

    /*@GetMapping("/{email}")
    public ResponseEntity<UserData> getUser(@PathVariable String email){
        User user = userService.getUserByEmail(email);
        return ResponseEntity.ok(JSONMapper.entityToUserData(user));
    }*/

    @GetMapping("/authenticate")
    public ResponseEntity<Boolean> getUser(final UserLogin loginData){
        User user = userService.getUserByEmail(loginData.getEmail());
        if(user.getPassword().equals(loginData.getPassword())){
            map.put(loginData.getEmail(), true);
            return ResponseEntity.ok(true);
        } else {
            return ResponseEntity.ok(false);
        }
    }



}
