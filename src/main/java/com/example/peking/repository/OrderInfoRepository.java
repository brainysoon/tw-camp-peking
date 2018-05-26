package com.example.peking.repository;

import com.example.peking.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Integer> {
}
