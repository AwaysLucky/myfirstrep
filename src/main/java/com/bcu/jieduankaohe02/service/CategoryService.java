package com.bcu.jieduankaohe02.service;

import com.bcu.jieduankaohe02.entity.Product;

import java.util.List;

public interface CategoryService {
    List<String> getAllCategories();
    List<Product> getProductsByCategory(String category);
}