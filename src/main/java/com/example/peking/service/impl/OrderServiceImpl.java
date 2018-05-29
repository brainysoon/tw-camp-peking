package com.example.peking.service.impl;

import com.example.peking.constant.StatusConstants;
import com.example.peking.entity.LogisticsRecords;
import com.example.peking.entity.Order;
import com.example.peking.entity.OrderInfo;
import com.example.peking.entity.Product;
import com.example.peking.exception.BusinessException;
import com.example.peking.repository.LogisticsRecordsRepository;
import com.example.peking.repository.OrderInfoRepository;
import com.example.peking.repository.OrderRepository;
import com.example.peking.repository.ProductRepository;
import com.example.peking.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    private static final String ORDER_IS_PROCESSED = "order is processed";
    private static final String PRODUCT_COUNT_NOT_ENOUGH = "product count not enough";
    private static final String NO_SUCH_PRODUCT = "no such product";

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
        Order order = generateOrder();
        order = orderRepository.save(order);

        Double totalPrice = persistenceOrderInfos(orderInfoList, order);
        order.setTotalPrice(totalPrice);
        return orderRepository.save(order);
    }

    private Double persistenceOrderInfos(List<OrderInfo> orderInfoList, Order order) throws Exception {
        Double totalPrice = 0.0D;
        for (OrderInfo orderInfo : orderInfoList) {
            Product product = productRepository.findById(orderInfo.getProductId())
                    .orElseThrow(() -> new BusinessException(NO_SUCH_PRODUCT, HttpStatus.NO_CONTENT));

            if (checkLockedCount(orderInfo, product)) {
                throw new BusinessException(PRODUCT_COUNT_NOT_ENOUGH, HttpStatus.NOT_ACCEPTABLE);
            }

            orderInfo.setCreateTime(new Date());
            orderInfo.setModifiedTime(orderInfo.getCreateTime());
            orderInfo.setOrderId(order.getId());
            orderInfo.setStatus(StatusConstants.ORDER_INFO_PRODUCT_COUNT_LOCK);

            totalPrice += product.getPrice() * orderInfo.getPurchaseCount();
        }

        orderInfoRepository.saveAll(orderInfoList);
        return totalPrice;
    }

    private boolean checkLockedCount(OrderInfo orderInfo, Product product) {
        List<OrderInfo> lockedOrderInfoList = orderInfoRepository.findByProductIdAndStatus(orderInfo.getProductId(),
                StatusConstants.ORDER_INFO_PRODUCT_COUNT_LOCK);
        Integer lockedCount = lockedOrderInfoList.stream().mapToInt(OrderInfo::getPurchaseCount).sum();
        return product.getCount() < orderInfo.getPurchaseCount() + lockedCount;
    }

    private Order generateOrder() {
        Order order = new Order();
        order.setCreateTime(new Date());
        order.setModifiedTime(order.getCreateTime());
        order.setStatus(StatusConstants.ACTIVE);
        order.setTotalPrice(0.0D);
        order.setUserId(1);
        return order;
    }

    @Override
    @Transactional
    public Order purchase(Integer id) throws Exception {
        Order order = orderRepository.findById(id).orElse(null);
        if (order == null) return null;

        if (order.getStatus() > 1) {
            throw new BusinessException(ORDER_IS_PROCESSED, HttpStatus.CONFLICT);
        }

        Date currentTime = new Date();
        order.setStatus(StatusConstants.ORDER_PURCHASED);
        order.setModifiedTime(currentTime);

        LogisticsRecords logisticsRecords = new LogisticsRecords();
        logisticsRecords.setOrderId(id);
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
            throw new BusinessException(ORDER_IS_PROCESSED, HttpStatus.CONFLICT);
        }

        Date currentTime = new Date();
        order.setStatus(StatusConstants.ORDER_WITHDRAWN);
        order.setModifiedTime(currentTime);
        unlockOrderInfoCount(order);
        return orderRepository.save(order);
    }

    private void unlockOrderInfoCount(Order order) {
        List<OrderInfo> orderInfos = orderInfoRepository.findByOrderId(order.getId());
        for (OrderInfo orderInfo : orderInfos) {
            orderInfo.setModifiedTime(new Date());
            orderInfo.setStatus(StatusConstants.ORDER_INFO_PRODUCT_COUNT_UNLOCK);
            orderInfoRepository.save(orderInfo);
        }
    }

    @Override
    public Order findById(Integer id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public List<Order> findByUserId(Integer userId) {
        return orderRepository.findByUserId(userId);
    }
}