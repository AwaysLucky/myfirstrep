package com.bcu.jieduankaohe02.service.impl;

import com.bcu.jieduankaohe02.dao.CartItemDao;
import com.bcu.jieduankaohe02.dao.ProductDao;
import com.bcu.jieduankaohe02.dao.ShoppingCartDao;
import com.bcu.jieduankaohe02.entity.*;
import com.bcu.jieduankaohe02.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class ShoppingCartServiceImpl implements ShoppingCartService {

    @Autowired
    private ShoppingCartDao shoppingCartDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Autowired
    private ProductDao productDao;

    @Override
    public void addToCart(Long userId, Long productId, int quantity) {
        ShoppingCart cart = shoppingCartDao.findByUserId(userId);
        if (cart == null) {
            cart = new ShoppingCart();
            cart.setUserId(userId);
            cart.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            cart.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            cart = shoppingCartDao.save(cart);
        }

        CartItem item = cartItemDao.findByCartIdAndProductId(cart.getCartId(), productId);
        if (item != null) {
            item.setQuantity(item.getQuantity() + quantity);
        } else {
            item = new CartItem();
            item.setCartId(cart.getCartId());
            item.setProductId(productId);
            item.setQuantity(quantity);
            item.setAddedAt(new Timestamp(System.currentTimeMillis()));
        }
        cartItemDao.save(item);
    }

    @Override
    public List<CartItem> getCartItemsByUserId(Long userId) {
        ShoppingCart cart = shoppingCartDao.findByUserId(userId);
        if (cart == null) return List.of();
        return cartItemDao.findByCartId(cart.getCartId());
    }

    @Override
    public void updateQuantity(Long userId, Long productId, int change) {
        ShoppingCart cart = shoppingCartDao.findByUserId(userId);
        if (cart == null) return;
        CartItem item = cartItemDao.findByCartIdAndProductId(cart.getCartId(), productId);
        if (item != null) {
            int newQty = item.getQuantity() + change;
            if (newQty <= 0) {
                cartItemDao.delete(item);
            } else {
                item.setQuantity(newQty);
                cartItemDao.save(item);
            }
        }
    }

    @Override
    public void removeFromCart(Long userId, Long productId) {
        ShoppingCart cart = shoppingCartDao.findByUserId(userId);
        if (cart != null) {
            CartItem item = cartItemDao.findByCartIdAndProductId(cart.getCartId(), productId);
            if (item != null) {
                cartItemDao.delete(item);
            }
        }
    }
}