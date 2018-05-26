package com.example.peking.controller;

import com.example.peking.constant.UriConstants;
import com.example.peking.entity.Order;
import com.example.peking.entity.OrderInfo;
import com.example.peking.service.OrderService;
import com.example.peking.util.URIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(UriConstants.ORDERS)
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    HttpEntity<Order> create(@RequestBody List<OrderInfo> orderInfoList) throws Exception {

        Order order = orderService.create(orderInfoList);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URIUtils.getNewResourcesLocation(UriConstants.ORDERS, order.getId().toString()));
        return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
    }
}
