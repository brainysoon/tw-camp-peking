package com.example.peking.service.impl;

import com.example.peking.constant.StatusConstants;
import com.example.peking.entity.LogisticsRecords;
import com.example.peking.entity.Order;
import com.example.peking.entity.OrderInfo;
import com.example.peking.entity.Product;
import com.example.peking.repository.LogisticsRecordsRepository;
import com.example.peking.repository.OrderInfoRepository;
import com.example.peking.repository.OrderRepository;
import com.example.peking.repository.ProductRepository;
import com.example.peking.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private LogisticsRecordsRepository logisticsRecordsRepository;

    @Override
    @Transactional
    public Order create(List<OrderInfo> orderInfoList) throws Exception {
        Date currentTime = new Date();
        List<Product> products = new ArrayList<>();
        Double total = 0.0D;

        Order order = new Order();
        order.setCreateTime(currentTime);
        order.setModifiedTime(currentTime);
        order.setStatus(StatusConstants.ACTIVE);
        order.setTotalPrice(total);
        order.setUserId((int) (Math.random() * 1000));
        order = orderRepository.save(order);

        for (OrderInfo orderInfo : orderInfoList) {
            Product product = productRepository.findById(orderInfo.getProductId()).orElseThrow(() -> new Exception("no such product"));
            if (product.getCount() < orderInfo.getPurchaseCount()) {
                throw new Exception("product is not enough");
            }
            product.setCount(product.getCount() - orderInfo.getPurchaseCount());
            product.setModifiedTime(currentTime);
            products.add(product);

            orderInfo.setModifiedTime(currentTime);
            orderInfo.setCreateTime(currentTime);
            orderInfo.setOrderId(order.getId());

            orderInfo.setStatus(StatusConstants.ORDER_INFO_PRODUCT_COUNT_LOCK);

            total += product.getPrice() * orderInfo.getPurchaseCount();
        }

        productRepository.saveAll(products);
        orderInfoRepository.saveAll(orderInfoList);
        return order;
    }

    @Override
    @Transactional
    public Order purchase(Integer id) throws Exception {

        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;

        if (order.getStatus() > 1) {
            throw new Exception("order is processed");
        }

        Date currentTime = new Date();
        order.setStatus(StatusConstants.ORDER_PURCHASED);
        order.setModifiedTime(currentTime);

        LogisticsRecords logisticsRecords = new LogisticsRecords();
        logisticsRecords.setCreateTime(currentTime);
        logisticsRecords.setLogisticsStatus(StatusConstants.ACTIVE);
        logisticsRecords.setModifiedTime(currentTime);

        logisticsRecordsRepository.save(logisticsRecords);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order withdrawn(Integer id) throws Exception {

        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;

        if (order.getStatus() > 1) {
            throw new Exception("order is processed");
        }

        Date currentTime = new Date();
        order.setStatus(StatusConstants.ORDER_WITHDRAWN);
        order.setModifiedTime(currentTime);

        List<OrderInfo> orderInfos = orderInfoRepository.findByOrderId(order.getId());
        for (OrderInfo orderInfo : orderInfos) {
            Product product = productRepository.findById(orderInfo.getProductId()).orElseThrow(() -> new Exception("no such product"));
            product.setModifiedTime(currentTime);
            product.setCount(product.getCount() + orderInfo.getProductId());

            orderInfo.setModifiedTime(currentTime);
            orderInfo.setStatus(StatusConstants.ORDER_INFO_PRODUCT_COUNT_UNLOCK);
            orderRepository.save(order);
            orderInfoRepository.save(orderInfo);
        }

        return orderRepository.save(order);
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }
}