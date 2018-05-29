package com.example.peking.repository;


import com.example.peking.entity.Product;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Before
    public void setUp() throws Exception {
        //本地启动mysql，创建employee_db数据库
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:mysql://stage.icusin.com:3306/peking", "peking", "peking");
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void should_add_product_when_given_the_required_data() {
        Product product = new Product();
        product.setName("test1");
        product.setDescription("test desc");
        product.setPrice(12.23);
        product.setCount(0);
        product.setStatus((byte) 1);
        product.setModifiedTime(new Date());
        product.setCreateTime(new Date());

        product = productRepository.save(product);

        assertThat(product.getId()).isEqualTo(5);
    }

    @Test
    public void should_return_the_products_match_the_name_fuzzily_when_given_the_name() {
        String name = "no";

        List<Product> products = productRepository.findByNameContaining(name);

        assertThat(products.size()).isEqualTo(3);
    }

    @Test
    public void should_return_the_products_match_the_name_and_description_fuzzily_when_given_the_name_and_desc() {
        String name = "no";
        String desc = "node";

        List<Product> products = productRepository.findByNameContainingOrDescriptionContaining(name, desc);

        assertThat(products.size()).isEqualTo(4);
    }
}