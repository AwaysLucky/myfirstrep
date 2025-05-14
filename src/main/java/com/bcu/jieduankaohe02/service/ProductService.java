package com.bcu.jieduankaohe02.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.bcu.jieduankaohe02.entity.Product;

@Service
public interface ProductService {

    List<Product> getPopularProducts();
    Product getProductById(Long id); // 新增方法
}