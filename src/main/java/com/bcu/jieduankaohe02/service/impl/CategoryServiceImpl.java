package com.bcu.jieduankaohe02.service.impl;

import com.bcu.jieduankaohe02.dao.ProductDao;
import com.bcu.jieduankaohe02.entity.Product;
import com.bcu.jieduankaohe02.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<String> getAllCategories() {
        return Arrays.asList(Product.CATEGORIES);
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
        return productDao.findByCategory(category);
    }
}