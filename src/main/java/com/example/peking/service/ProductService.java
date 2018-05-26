package com.example.peking.service;

import com.example.peking.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductService {

    Product save(Product product);

    Product findById(Integer id);

    List<Product> findAll();

    Page<Product> findByPage(Pageable pageable);

    List<Product> findBy(Product query);

    Product update(Product product);

    Product inventories(Integer id, Integer count);
}