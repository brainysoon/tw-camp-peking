package com.example.peking.service;

import com.example.peking.entity.Order;
import com.example.peking.entity.OrderInfo;

import java.util.List;

public interface OrderService {

    Order create(List<OrderInfo> orderInfoList);

    Order purchase(Integer id);

    Order withdrawn(Integer id);

    Order findById(Integer id);

    List<Order> findByUserId(Integer userId);
}
