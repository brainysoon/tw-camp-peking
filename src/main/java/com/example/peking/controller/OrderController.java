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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UriConstants.ORDERS)
public class OrderController {

    private static final String PAID = "paid";
    private static final String WITHDRAWN = "withdrawn";

    @Autowired
    private OrderService orderService;

    @PostMapping
    HttpEntity<Order> create(@RequestBody List<OrderInfo> orderInfoList) {

        Order order = orderService.create(orderInfoList);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URIUtils.getNewResourcesLocation(UriConstants.ORDERS, order.getId().toString()));
        return new ResponseEntity<>(order, headers, HttpStatus.CREATED);
    }

    @PutMapping(UriConstants.ID)
    HttpEntity<Order> update(@PathVariable Integer id, @RequestParam String orderStatus) {

        if (PAID.equals(orderStatus)) {
            Order order = orderService.purchase(id);
            return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
        }

        if (WITHDRAWN.equals(orderStatus)) {
            Order order = orderService.withdrawn(id);
            return new ResponseEntity<>(order, HttpStatus.ACCEPTED);
        }

        return ResponseEntity.badRequest().build();
    }

    @GetMapping(UriConstants.ID)
    HttpEntity<Order> findById(@PathVariable Integer id) {

        Order order = orderService.findById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @GetMapping
    HttpEntity<List<Order>> findByUserId(@RequestParam Integer userId) {

        List<Order> orders = orderService.findByUserId(userId);
        if (orders.isEmpty())
            return ResponseEntity.noContent().build();

        return new ResponseEntity<>(orders, HttpStatus.OK);
    }
}