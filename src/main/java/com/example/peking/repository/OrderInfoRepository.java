package com.example.peking.repository;

import com.example.peking.entity.OrderInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderInfoRepository extends JpaRepository<OrderInfo, Integer> {

    List<OrderInfo> findByOrderId(Integer orderId);

    List<OrderInfo> findByProductIdAndStatus(Integer productId, Byte status);
}