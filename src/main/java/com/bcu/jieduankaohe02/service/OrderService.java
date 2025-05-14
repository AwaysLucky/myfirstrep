package com.bcu.jieduankaohe02.service;

import com.bcu.jieduankaohe02.entity.CartItem;
import com.bcu.jieduankaohe02.entity.OrderTable;
import com.bcu.jieduankaohe02.entity.OrderDetail;

import java.util.List;

public interface OrderService {
    void createOrderFromCart(Long userId);
    List<OrderTable> getOrdersByUserId(Long userId);
    void completeOrder(Long orderId);
    List<OrderDetail> getOrderDetailsByOrderId(Long orderId);
}