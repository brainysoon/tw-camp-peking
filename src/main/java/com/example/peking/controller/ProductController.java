package com.example.peking.controller;

import com.example.peking.constant.UriConstants;
import com.example.peking.entity.Product;
import com.example.peking.service.ProductService;
import com.example.peking.util.URIUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(UriConstants.PRODUCTS)
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping
    HttpEntity<Product> saveProduct(@RequestBody Product product) {

        Product savedProduct = productService.save(product);

        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(URIUtils.getNewResourcesLocation(UriConstants.PRODUCTS, savedProduct.getId().toString()));
        return new ResponseEntity<>(savedProduct, headers, HttpStatus.CREATED);
    }

    @GetMapping
    HttpEntity<List<Product>> getProducts(Product query) {

        List<Product> allProduct = productService.findBy(query);
        if (allProduct.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(allProduct, HttpStatus.OK);
    }

    @GetMapping(UriConstants.PAGE_PAGESIZE)
    HttpEntity<Page<Product>> getByPage(@PathVariable Integer page, @PathVariable Integer pagesize) {

        Pageable pageable = PageRequest.of(page, pagesize);
        Page<Product> productPage = productService.findByPage(pageable);
        return new ResponseEntity<>(productPage, HttpStatus.OK);
    }

    @PutMapping(UriConstants.ID)
    HttpEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {

        product.setId(id);
        Product updatedProduct = productService.update(product);
        if (updatedProduct == null) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(updatedProduct, HttpStatus.ACCEPTED);
    }

    @GetMapping(UriConstants.ID)
    HttpEntity<Product> getProduct(@PathVariable Integer id) {

        Product product = productService.findById(id);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }
}
