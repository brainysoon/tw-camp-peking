package com.example.peking.service;

import com.example.peking.entity.Product;
import com.example.peking.repository.ProductRepository;
import com.example.peking.service.impl.ProductServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService = new ProductServiceImpl();


    @Test
    public void should_save_product_when_given_the_name_desc_and_price() {
        Product product = new Product();
        product.setName("test");
        product.setDescription("test desc");
        product.setPrice(12.0);
        Product savedProduct = new Product();
        savedProduct.setId(3);

        given(productRepository.save(product)).willReturn(savedProduct);

        savedProduct = productService.save(product);

        assertThat(savedProduct.getId()).isEqualTo(3);
    }

    @Test
    public void should_return_product_when_given_the_id() {
        Integer productId = 1;
        Product product = new Product();
        product.setId(1);
        Optional<Product> productOptional = Optional.of(product);
        given(productRepository.findById(productId)).willReturn(productOptional);

        product = productService.findById(productId);

        assertThat(product).isNotNull();
        assertThat(product.getId()).isEqualTo(1);
    }

    @Test
    public void should_return_all_the_products() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        given(productRepository.findAll()).willReturn(products);

        products = productService.findAll();

        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    public void should_return_page_when_find_by_page() {
        List<Product> products = new ArrayList<>();
        products.add(new Product());
        products.add(new Product());
        Pageable pageable = PageRequest.of(1, 2);
        Page<Product> productPage = new PageImpl<>(products, pageable, 6);
        given(productRepository.findAll(pageable)).willReturn(productPage);

        productPage = productService.findByPage(pageable);

        assertThat(productPage.getTotalPages()).isEqualTo(3);
        assertThat(productPage.getSize()).isEqualTo(2);
    }

    @Test
    public void should_return_products_that_match_the_given_name() {
        Product product = new Product();
        product.setName("no");
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        given(productRepository.findByNameContaining(product.getName())).willReturn(productList);

        List<Product> products = productService.findBy(product);

        assertThat(products.size()).isEqualTo(1);
    }

    @Test
    public void should_return_products_that_match_the_given_name_or_desc() {
        Product product = new Product();
        product.setName("no");
        product.setDescription("node");
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product);
        given(productRepository.findByNameContainingOrDescriptionContaining(product.getName(), product.getDescription()))
                .willReturn(productList);

        List<Product> products = productService.findBy(product);

        assertThat(products.size()).isEqualTo(2);
    }

    @Test
    public void should_return_all_the_products_when_given_query_is_empty() {
        Product product = new Product();
        List<Product> productList = new ArrayList<>();
        productList.add(product);
        productList.add(product);
        productList.add(product);
        given(productRepository.findAll()).willReturn(productList);

        List<Product> products = productService.findBy(product);

        assertThat(products.size()).isEqualTo(3);
    }
}
