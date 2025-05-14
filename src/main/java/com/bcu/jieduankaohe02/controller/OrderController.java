package com.bcu.jieduankaohe02.controller;

import com.bcu.jieduankaohe02.entity.OrderDetail;
import com.bcu.jieduankaohe02.entity.OrderTable;
import com.bcu.jieduankaohe02.entity.User;
import com.bcu.jieduankaohe02.service.OrderService;
import com.bcu.jieduankaohe02.service.ShoppingCartService;
import com.bcu.jieduankaohe02.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private UserRepository userRepository;

    // 获取当前登录用户ID
    private Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return getUserIdFromUsername(username);
    }

    private Long getUserIdFromUsername(String username) {
        // 使用已有的方法查找用户
        User user = userRepository.findByUsernameOrEmail(username);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        return user.getUserId();
    }

    // 查看订单页面
    @GetMapping
    public String viewOrders(Model model) {
        Long userId = getCurrentUserId();
        List<OrderTable> orders = orderService.getOrdersByUserId(userId);
        model.addAttribute("orders", orders);
        return "orders";
    }

    // 下单接口（从购物车生成订单）
    @PostMapping("/create")
    public String createOrder() {
        Long userId = getCurrentUserId();
        orderService.createOrderFromCart(userId);
        return "redirect:/orders";
    }

    // 支付接口（仅修改状态）
    @PostMapping("/complete/{orderId}")
    public String completeOrder(@PathVariable Long orderId) {
        orderService.completeOrder(orderId);
        return "redirect:/orders";
    }
    @GetMapping("/{id}")
    public String viewOrderDetails(@PathVariable("id") Long orderId, Model model) {
        List<OrderDetail> details = orderService.getOrderDetailsByOrderId(orderId);

        for (OrderDetail detail : details) {
            if (detail.getUnitPrice() != null && detail.getQuantity() != null) {
                detail.setSubtotal(detail.getUnitPrice().multiply(BigDecimal.valueOf(detail.getQuantity())));
            } else {
                detail.setSubtotal(BigDecimal.ZERO);
            }
        }

        OrderTable order = details.get(0).getOrder();

        model.addAttribute("order", order);
        model.addAttribute("details", details);

        return "order-detail";
    }
}