package com.bcu.jieduankaohe02.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.sql.Timestamp;

@Entity
@Table(name = "product")
public class Product {

    @Id
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "stock", nullable = false)
    private Integer stock;

    @Column(name = "image_url", length = 512)
    private String imageUrl;

    @Column(name = "created_at", updatable = false)
    private Timestamp createdAt;

    @Column(name = "updated_at")
    private Timestamp updatedAt;

    // 非映射字段：用于在页面上显示类别（从name中提取）
    @Transient
    private String category;
    // Product.java中修改这两个字段
    @Column(name = "total_score", insertable = false, updatable = false)
    private Double totalScore;

    @Column(name = "rating_count", insertable = false, updatable = false)
    private Integer ratingCount;

    public static final String[] CATEGORIES = {
            "智能手机", "数码相机", "游戏主机", "家用电器", "个人护理",
            "无线耳机", "办公设备", "平板电脑", "家居装饰", "厨房电器",
            "智能电视", "服装鞋帽", "户外用品", "运动装备", "笔记本电脑", "智能手表"
    };


    // Getter 和 Setter
    public Double getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Double totalScore) {
        this.totalScore = totalScore;
    }

    public Integer getRatingCount() {
        return ratingCount;
    }

    public void setRatingCount(Integer ratingCount) {
        this.ratingCount = ratingCount;
    }
    public Product() {}

    // 构造方法（可选）
// 添加构造函数
    public Product(Long productId, String name, String description, BigDecimal price, Integer stock,
                   String imageUrl, Timestamp createdAt, Timestamp updatedAt, Double totalScore, Integer ratingCount) {
        this.productId = productId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.totalScore = totalScore;
        this.ratingCount = ratingCount;
    }

// Getter & Setter...

    // Getter 和 Setter 方法
    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }

    // 修改getCategory方法确保安全性
    public String getCategory() {
        if (this.name != null && this.name.contains("-")) {
            String category = this.name.split("-", 2)[0].trim();
            // 验证是否为有效类别
            for (String validCat : CATEGORIES) {
                if (validCat.equals(category)) {
                    return category;
                }
            }
        }
        return "其他";
    }

    public void setCategory(String category) {
        // 该方法为占位方法，不实际使用
    }
}