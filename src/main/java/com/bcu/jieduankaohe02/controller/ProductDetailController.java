package com.bcu.jieduankaohe02.controller;

import com.bcu.jieduankaohe02.entity.Product;
import com.bcu.jieduankaohe02.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ProductDetailController {

    @Autowired
    private ProductService productService;

    @GetMapping("/product/{productId}")
    public String showProductDetail(@PathVariable Long productId, Model model) {
        Product product = productService.getProductById(productId);
        if (product == null) {
            return "error/404"; // 假设你有404页面
        }
        model.addAttribute("product", product);
        return "product-detail";
    }
}