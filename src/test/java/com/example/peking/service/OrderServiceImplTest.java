package com.example.peking.service;

import com.example.peking.constant.StatusConstants;
import com.example.peking.entity.Order;
import com.example.peking.entity.OrderInfo;
import com.example.peking.entity.Product;
import com.example.peking.repository.LogisticsRecordsRepository;
import com.example.peking.repository.OrderInfoRepository;
import com.example.peking.repository.OrderRepository;
import com.example.peking.repository.ProductRepository;
import com.example.peking.service.impl.OrderServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(SpringRunner.class)
public class OrderServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private LogisticsRecordsRepository logisticsRecordsRepository;

    @Mock
    private OrderInfoRepository orderInfoRepository;

    @InjectMocks
    private OrderServiceImpl orderService = new OrderServiceImpl();


    @Test
    public void should_create_the_order_when_given_the_order_info_list() throws Exception {
        List<OrderInfo> orderInfoList = new ArrayList<>();
        OrderInfo orderInfo3 = new OrderInfo();
        orderInfo3.setProductId(1);
        orderInfo3.setPurchaseCount(2);
        OrderInfo orderInfo1 = new OrderInfo();
        orderInfo1.setProductId(2);
        orderInfo1.setPurchaseCount(3);
        OrderInfo orderInfo2 = new OrderInfo();
        orderInfo2.setProductId(3);
        orderInfo2.setPurchaseCount(4);
        orderInfoList.add(orderInfo3);
        orderInfoList.add(orderInfo1);
        orderInfoList.add(orderInfo2);

        Order savedOrder = new Order();
        savedOrder.setId(2);
        given(orderRepository.save(any())).willReturn(savedOrder);

        Product product = new Product();
        product.setId(savedOrder.getId());
        product.setCount(10);
        product.setPrice(10.0D);
        given(productRepository.findById(anyInt())).willReturn(Optional.of(product));

        given(productRepository.saveAll(any())).willReturn(null);
        given(orderInfoRepository.saveAll(any())).willReturn(orderInfoList);

        savedOrder = orderService.create(orderInfoList);

        assertThat(savedOrder.getId()).isEqualTo(2);
        verify(orderRepository, times(1)).save(any());
        verify(productRepository, times(3)).findById(anyInt());
        verify(productRepository).saveAll(any());
        verify(orderInfoRepository).saveAll(any());
    }

    @Test(expected = Exception.class)
    public void should_throw_exception_when_the_product_count_is_not_enough() throws Exception {
        List<OrderInfo> orderInfoList = new ArrayList<>();
        OrderInfo orderInfo3 = new OrderInfo();
        orderInfo3.setProductId(1);
        orderInfo3.setPurchaseCount(2);
        OrderInfo orderInfo1 = new OrderInfo();
        orderInfo1.setProductId(2);
        orderInfo1.setPurchaseCount(3);
        OrderInfo orderInfo2 = new OrderInfo();
        orderInfo2.setProductId(3);
        orderInfo2.setPurchaseCount(4);
        orderInfoList.add(orderInfo3);
        orderInfoList.add(orderInfo1);
        orderInfoList.add(orderInfo2);

        Order savedOrder = new Order();
        savedOrder.setId(2);
        given(orderRepository.save(any())).willReturn(savedOrder);

        Product product = new Product();
        product.setId(savedOrder.getId());
        product.setCount(1);
        product.setPrice(10.0D);
        given(productRepository.findById(anyInt())).willReturn(Optional.of(product));

        orderService.create(orderInfoList);
    }

    @Test
    public void should_return_null_when_the_given_order_is_not_present() throws Exception {
        given(orderRepository.findById(anyInt())).willReturn(Optional.empty());

        Order order = orderService.purchase(1);

        assertThat(order).isNull();
    }

    @Test(expected = Exception.class)
    public void should_throw_exception_when_the_given_order_is_been_processed() throws Exception {
        Order order = new Order();
        order.setStatus(StatusConstants.ORDER_PURCHASED);
        given(orderRepository.findById(anyInt())).willReturn(Optional.of(order));

        orderService.purchase(1);
    }

    @Test
    public void should_purchase_the_order_and_generate_the_logistic_records_when_purchase() throws Exception {
        Order order = new Order();
        order.setId(1);
        order.setStatus(StatusConstants.ACTIVE);
        given(orderRepository.findById(anyInt())).willReturn(Optional.of(order));
        given(orderRepository.save(any())).willReturn(order);
        given(logisticsRecordsRepository.save(any())).willReturn(null);


        order = orderService.purchase(1);

        verify(orderRepository).findById(anyInt());
        verify(orderRepository).save(any());
        verify(logisticsRecordsRepository).save(any());
    }

    @Test
    public void should_return_null_when_the_given_withdrawn_order_is_not_present() throws Exception {
        given(orderRepository.findById(anyInt())).willReturn(Optional.empty());

        Order order = orderService.withdrawn(1);

        assertThat(order).isNull();
    }

    @Test(expected = Exception.class)
    public void should_throw_exception_when_the_given_withdrawn_order_is_been_processed() throws Exception {
        Order order = new Order();
        order.setStatus(StatusConstants.ORDER_WITHDRAWN);
        given(orderRepository.findById(anyInt())).willReturn(Optional.of(order));

        orderService.withdrawn(1);
    }

    @Test
    public void should_withdrawn_the_order_when_given_the_order_id() throws Exception {
        Order order = new Order();
        order.setId(1);
        order.setStatus(StatusConstants.ACTIVE);
        given(orderRepository.findById(anyInt())).willReturn(Optional.of(order));

        List<OrderInfo> orderInfoList = new ArrayList<>();
        OrderInfo orderInfo3 = new OrderInfo();
        orderInfo3.setProductId(1);
        orderInfo3.setPurchaseCount(2);
        OrderInfo orderInfo1 = new OrderInfo();
        orderInfo1.setProductId(2);
        orderInfo1.setPurchaseCount(3);
        OrderInfo orderInfo2 = new OrderInfo();
        orderInfo2.setProductId(3);
        orderInfo2.setPurchaseCount(4);
        orderInfoList.add(orderInfo3);
        orderInfoList.add(orderInfo1);
        orderInfoList.add(orderInfo2);

        given(orderInfoRepository.findByOrderId(order.getId())).willReturn(orderInfoList);
        Product product = new Product();
        product.setCount(10);
        given(productRepository.findById(anyInt())).willReturn(Optional.of(product));
        given(orderInfoRepository.save(any())).willReturn(null);
        given(orderRepository.save(any())).willReturn(order);

        orderService.withdrawn(1);

        verify(orderRepository).findById(anyInt());
        verify(orderInfoRepository).findByOrderId(anyInt());
        verify(productRepository, times(3)).findById(anyInt());
        verify(orderRepository).save(any());
        verify(orderInfoRepository, times(3)).save(any());
    }

    @Test
    public void should_return_null_when_the_given_find_by_id_order_is_not_present() {
        given(orderRepository.findById(anyInt())).willReturn(Optional.empty());

        Order order = orderService.findById(1);

        assertThat(order).isNull();
    }
}
