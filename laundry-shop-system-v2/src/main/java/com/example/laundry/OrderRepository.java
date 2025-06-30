package com.example.laundry;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    /**
     * 依下單時間由舊到新排序
     */
    List<Order> findAllByOrderByOrderTimeAsc();
}