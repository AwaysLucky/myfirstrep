package com.bcu.jieduankaohe02.dao;

import com.bcu.jieduankaohe02.entity.OrderTable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderTableDao extends JpaRepository<OrderTable, Long> {
    List<OrderTable> findByUserId(Long userId);
}