package com.bcu.jieduankaohe02.controller;

import com.bcu.jieduankaohe02.entity.Product;
import com.bcu.jieduankaohe02.service.ProductService;
import com.bcu.jieduankaohe02.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;

@Controller
public class HomeController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/home")
    public String home(Authentication authentication, Model model) {
        if (authentication != null && authentication.isAuthenticated()) {
            model.addAttribute("username", authentication.getName());

            // 初始加载热门商品
            model.addAttribute("products", productService.getPopularProducts());

            // 添加分类数据
            model.addAttribute("categories", Arrays.asList(Product.CATEGORIES));
        } else {
            return "redirect:/login";
        }
        return "home";
    }

}