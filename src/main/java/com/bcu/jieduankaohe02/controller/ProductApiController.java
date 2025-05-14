package com.bcu.jieduankaohe02.controller;

import com.bcu.jieduankaohe02.entity.Product;
import com.bcu.jieduankaohe02.service.CategoryService;
import com.bcu.jieduankaohe02.dao.ProductDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductApiController {

    @Autowired
    private ProductDao productDao;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/products")
    public List<Product> getProducts(@RequestParam(required = false) String category) {
        if (category == null || category.isEmpty()) {
            return productDao.findPopularProducts();
        } else {
            return productDao.findByCategory(category);
        }
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return categoryService.getAllCategories();
    }
}