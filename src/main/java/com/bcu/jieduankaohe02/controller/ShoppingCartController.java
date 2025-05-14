package com.bcu.jieduankaohe02.controller;

import com.bcu.jieduankaohe02.entity.CartItem;
import com.bcu.jieduankaohe02.entity.Product;
import com.bcu.jieduankaohe02.entity.User;
import com.bcu.jieduankaohe02.repository.UserRepository;
import com.bcu.jieduankaohe02.service.ShoppingCartService;
import com.bcu.jieduankaohe02.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class ShoppingCartController {

    @Autowired
    private ShoppingCartService cartService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping
    public String viewCart(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();

        // 获取当前登录用户 ID
        Long userId = getUserIdFromUsername(username);

        List<CartItem> items = cartService.getCartItemsByUserId(userId);
        BigDecimal total = BigDecimal.ZERO;

        for (CartItem item : items) {
            Product product = productService.getProductById(item.getProductId());
            item.setProduct(product);
            BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(item.getQuantity()));
            item.setSubtotal(subtotal); // 新增字段或临时变量
            total = total.add(subtotal); // 累加总价（修复点）
        }
        model.addAttribute("cartItems", items);
        model.addAttribute("totalAmount", total);
        return "cart";
    }

    @GetMapping("/add")
    public String addToCart(@RequestParam("productId") Long productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Long userId = getUserIdFromUsername(username);
        cartService.addToCart(userId, productId, 1);
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateQuantity(
            @RequestParam("productId") Long productId,
            @RequestParam("change") int change) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Long userId = getUserIdFromUsername(username);

        cartService.addToCart(userId, productId, change);
        return "redirect:/cart";
    }

    @GetMapping("/remove")
    public String removeItem(@RequestParam("productId") Long productId) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Long userId = getUserIdFromUsername(username);
        cartService.removeFromCart(userId, productId);
        return "redirect:/cart";
    }

    // 根据用户名获取用户ID
    private Long getUserIdFromUsername(String username) {
        User user = userRepository.findByUsernameOrEmail(username);
        if (user == null) {
            throw new IllegalArgumentException("用户不存在");
        }
        return user.getUserId();
    }
}