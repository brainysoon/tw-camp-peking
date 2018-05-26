package com.example.peking.service.impl;

import com.example.peking.constant.StatusConstants;
import com.example.peking.entity.Order;
import com.example.peking.entity.OrderInfo;
import com.example.peking.entity.Product;
import com.example.peking.entity.ProductLock;
import com.example.peking.repository.OrderInfoRepository;
import com.example.peking.repository.OrderRepository;
import com.example.peking.repository.ProductLockRepository;
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
    private ProductLockRepository productLockRepository;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Override
    @Transactional
    public Order create(List<OrderInfo> orderInfoList) throws Exception {
        Date currentTime = new Date();
        List<Product> products = new ArrayList<>();
        List<ProductLock> productLocks = new ArrayList<>();
        Double total = 0.0D;

        for (OrderInfo orderInfo : orderInfoList) {
            Product product = productRepository.findById(orderInfo.getProductId()).orElseThrow(() -> new Exception("no such product"));
            if (product.getCount() < orderInfo.getPurchaseCount()) {
                throw new Exception("product is not enough");
            }
            product.setCount(product.getCount() - orderInfo.getPurchaseCount());
            product.setModifiedTime(currentTime);
            products.add(product);

            ProductLock productLock = new ProductLock();
            productLock.setProductId(product.getId());
            productLock.setCount(orderInfo.getPurchaseCount());
            productLock.setCreateTime(currentTime);
            productLock.setModifiedTime(currentTime);
            productLock.setStatus(StatusConstants.ACTIVE);
            productLocks.add(productLock);

            orderInfo.setModifiedTime(currentTime);
            orderInfo.setCreateTime(currentTime);
            orderInfo.setStatus(StatusConstants.ACTIVE);

            total += product.getPrice() * orderInfo.getPurchaseCount();
        }

        Order order = new Order();
        order.setCreateTime(currentTime);
        order.setModifiedTime(currentTime);
        order.setStatus(StatusConstants.ACTIVE);
        order.setTotal(total);
        order.setUserId((int) (Math.random() * 1000));

        productRepository.saveAll(products);
        productLockRepository.saveAll(productLocks);
        orderInfoRepository.saveAll(orderInfoList);
        return orderRepository.save(order);
    }
}