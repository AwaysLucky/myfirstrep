package com.bcu.jieduankaohe02.service.impl;

import com.bcu.jieduankaohe02.entity.Product;
import com.bcu.jieduankaohe02.dao.ProductDao;
import com.bcu.jieduankaohe02.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    @Override
    public List<Product> getPopularProducts() {
        return productDao.findPopularProducts();
    }

    @Override
    public Product getProductById(Long id) {
        return productDao.findById(id).orElse(null);
    }
}