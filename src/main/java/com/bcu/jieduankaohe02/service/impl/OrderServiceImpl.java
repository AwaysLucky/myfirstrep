package com.bcu.jieduankaohe02.service.impl;

import com.bcu.jieduankaohe02.dao.*;
import com.bcu.jieduankaohe02.entity.*;
import com.bcu.jieduankaohe02.service.OrderService;
import com.bcu.jieduankaohe02.service.ProductService;
import com.bcu.jieduankaohe02.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderTableDao orderTableDao;

    @Autowired
    private OrderDetailDao orderDetailDao;

    @Autowired
    private CartItemDao cartItemDao;

    @Override
    @Transactional
    public void createOrderFromCart(Long userId) {
        List<CartItem> items = shoppingCartService.getCartItemsByUserId(userId);
        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("购物车为空，无法下单");
        }

        // 创建订单主表
        OrderTable order = new OrderTable();
        order.setUserId(userId);
        order.setStatus("unpaid");
        order.setCreatedAt(new Timestamp(System.currentTimeMillis()));
        order.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
        order.setTotalAmount(BigDecimal.ZERO); // 👈 初始设置为0

        // 先保存订单主表，以生成 orderId
        order = orderTableDao.save(order);

        BigDecimal total = BigDecimal.ZERO;

        // 遍历购物车项并创建订单详情
        for (CartItem item : items) {
            Product product = productService.getProductById(item.getProductId());
            if (product == null) continue;

            OrderDetail detail = new OrderDetail();
            detail.setOrderId(order.getOrderId());
            detail.setProductId(product.getProductId());
            detail.setQuantity(item.getQuantity());
            detail.setUnitPrice(product.getPrice());

            orderDetailDao.save(detail);

            total = total.add(product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // 更新总金额
        order.setTotalAmount(total);
        orderTableDao.save(order);

        // 清空购物车
        for (CartItem item : items) {
            cartItemDao.delete(item);
        }
    }


    @Override
    public List<OrderTable> getOrdersByUserId(Long userId) {
        return orderTableDao.findByUserId(userId);
    }

    @Override
    public void completeOrder(Long orderId) {
        OrderTable order = orderTableDao.findById(orderId).orElse(null);
        if (order != null && "unpaid".equals(order.getStatus())) {
            order.setStatus("completed");
            order.setUpdatedAt(new Timestamp(System.currentTimeMillis()));
            orderTableDao.save(order);
        }
    }

    @Override
    public List<OrderDetail> getOrderDetailsByOrderId(Long orderId) {
        return orderDetailDao.findByOrderId(orderId);
    }
}