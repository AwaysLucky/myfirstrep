package com.bcu.jieduankaohe02.controller;

import com.bcu.jieduankaohe02.entity.Product;
import com.bcu.jieduankaohe02.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/category/{category}")
    public String showCategory(@PathVariable String category, Model model) {
        model.addAttribute("products", categoryService.getProductsByCategory(category));
        model.addAttribute("currentCategory", category);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "category"; // 对应的视图名称
    }
}