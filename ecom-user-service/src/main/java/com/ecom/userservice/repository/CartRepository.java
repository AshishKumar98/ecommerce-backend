package com.ecom.userservice.repository;

import com.ecom.userservice.entity.Cart;
import com.ecom.userservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {
    @Query(value = "SELECT c FROM Cart c WHERE c.userID = ?1")
    Cart findBYUserID(Long userID);

}
