package com.bcu.jieduankaohe02.dao;

import com.bcu.jieduankaohe02.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductDao extends JpaRepository<Product, Long> {

    // 查询热门商品的SQL语句
    @Query(value = "SELECT " +
            "p.product_id, " +
            "p.name, " +
            "p.description, " +
            "p.price, " +
            "p.stock, " +
            "p.image_url, " +
            "p.created_at, " +
            "p.updated_at, " +
            "SUM(upp.score) AS total_score, " +
            "COUNT(*) AS rating_count " +
            "FROM product p " +
            "JOIN user_product_preference upp ON p.product_id = upp.product_id " +
            "GROUP BY p.product_id " +
            "ORDER BY total_score DESC, rating_count DESC " +
            "LIMIT 12", nativeQuery = true)
    List<Product> findPopularProducts();

    @Query("SELECT p FROM Product p WHERE p.name LIKE CONCAT(:category, '-%')")
    List<Product> findByCategory(@Param("category") String category);
}