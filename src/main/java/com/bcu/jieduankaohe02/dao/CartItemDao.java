package com.bcu.jieduankaohe02.dao;

import com.bcu.jieduankaohe02.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemDao extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCartId(Long cartId);
    CartItem findByCartIdAndProductId(Long cartId, Long productId);
}