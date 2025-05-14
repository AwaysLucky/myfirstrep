package com.bcu.jieduankaohe02.service;

import com.bcu.jieduankaohe02.entity.CartItem;

import java.util.List;

public interface ShoppingCartService {
    void addToCart(Long userId, Long productId, int quantity);
    List<CartItem> getCartItemsByUserId(Long userId);
    void updateQuantity(Long userId, Long productId, int change);
    void removeFromCart(Long userId, Long productId);
}