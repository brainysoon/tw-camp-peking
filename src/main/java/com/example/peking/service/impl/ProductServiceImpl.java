package com.example.peking.service.impl;

import com.example.peking.constant.StatusConstants;
import com.example.peking.entity.Product;
import com.example.peking.repository.ProductRepository;
import com.example.peking.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public Product save(Product product) {

        Date createDate = new Date();
        product.setCount(0);
        product.setCreateTime(createDate);
        product.setModifiedTime(createDate);
        product.setStatus(StatusConstants.ACTIVE);

        return productRepository.save(product);
    }

    @Override
    public Product findById(Integer id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public Page<Product> findByPage(Pageable pageable) {
        return productRepository.findAll(pageable);
    }

    @Override
    public List<Product> findBy(Product query) {

        if (query.getName() != null && query.getDescription() != null) {
            return productRepository.findByNameContainingOrDescriptionContaining(query.getName(),
                    query.getDescription());
        }

        if (query.getName() != null) {
            return productRepository.findByNameContaining(query.getName());
        }

        return productRepository.findAll();
    }

    @Override
    public Product update(Product product) {

        Product currentProduct = productRepository.findById(product.getId()).orElse(null);
        if (currentProduct == null) return null;

        product.setCreateTime(currentProduct.getCreateTime());
        product.setModifiedTime(new Date());
        product.setStatus(currentProduct.getStatus());
        if (product.getCount() == null) product.setCount(currentProduct.getCount());

        return productRepository.save(product);
    }

    @Override
    public Product inventories(Integer id, Integer count) {
        Product product = productRepository.findById(id).orElse(null);
        if (product == null) return null;

        product.setCount(count);
        product.setModifiedTime(new Date());
        return productRepository.save(product);
    }
}
