package com.example.peking.repository;

import com.example.peking.entity.OrderInfo;
import org.flywaydb.core.Flyway;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.NONE;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = NONE)
public class OrderInfoRepositoryTest {

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Before
    public void setUp() throws Exception {
        //本地启动mysql，创建employee_db数据库
        Flyway flyway = new Flyway();
        flyway.setDataSource("jdbc:mysql://stage.icusin.com:3306/peking", "peking", "peking");
        flyway.clean();
        flyway.migrate();
    }

    @Test
    public void should_return_all_the_order_info_of_given_order_id() {

        List<OrderInfo> orderInfoList = orderInfoRepository.findByOrderId(1);

        assertThat(orderInfoList.size()).isEqualTo(3);
    }
}
