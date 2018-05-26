package com.example.peking.controller;

import com.example.peking.constant.UriConstants;
import com.example.peking.entity.Product;
import com.example.peking.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UriConstants.INVENTORIES)
public class InventoriesController {

    @Autowired
    private ProductService productService;

    @PutMapping(UriConstants.ID)
    HttpEntity<Product> inventories(@PathVariable Integer id, @RequestBody Product product) {

        Product updatedProduct = productService.inventories(id, product.getCount());
        if (updatedProduct == null) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(updatedProduct, HttpStatus.OK);
    }
}