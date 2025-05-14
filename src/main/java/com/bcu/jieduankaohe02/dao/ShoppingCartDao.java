package com.bcu.jieduankaohe02.dao;

import com.bcu.jieduankaohe02.entity.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartDao extends JpaRepository<ShoppingCart, Long> {
    ShoppingCart findByUserId(Long userId);
}