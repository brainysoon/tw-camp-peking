package com.example.peking.service;

import com.example.peking.entity.LogisticsRecords;
import com.example.peking.entity.OrderInfo;
import com.example.peking.entity.Product;
import com.example.peking.repository.LogisticsRecordsRepository;
import com.example.peking.repository.OrderInfoRepository;
import com.example.peking.repository.ProductRepository;
import com.example.peking.service.impl.LogisticsRecordsServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@RunWith(SpringRunner.class)
public class LogisticsRecordsServiceImplTest {

    @Mock
    private OrderInfoRepository orderInfoRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private LogisticsRecordsRepository logisticsRecordsRepository;

    @InjectMocks
    private LogisticsRecordsServiceImpl logisticsRecordsService = new LogisticsRecordsServiceImpl();

    @Test
    public void should_return_null_when_the_given_logistics_id_is_not_present() {
        given(logisticsRecordsRepository.findById(anyInt())).willReturn(Optional.empty());

        LogisticsRecords logisticsRecords = logisticsRecordsService.shipping(1);

        assertThat(logisticsRecords).isNull();
    }

    @Test
    public void should_shipping_the_logistics_when_given_the_id() {
        LogisticsRecords logisticsRecords = new LogisticsRecords();
        given(logisticsRecordsRepository.findById(anyInt())).willReturn(Optional.of(logisticsRecords));
        given(logisticsRecordsRepository.save(any())).willReturn(logisticsRecords);

        logisticsRecordsService.shipping(1);

        verify(logisticsRecordsRepository).save(any());
    }

    @Test
    public void should_return_null_when_the_given_signed_logistics_id_is_not_present() throws Exception {
        given(logisticsRecordsRepository.findById(anyInt())).willReturn(Optional.empty());

        LogisticsRecords logisticsRecords = logisticsRecordsService.signed(1);

        assertThat(logisticsRecords).isNull();
    }

    @Test
    public void should_minus_count_and_update_info_status_when_signed() throws Exception {
        LogisticsRecords logisticsRecords = new LogisticsRecords();
        logisticsRecords.setOrderId(1);
        given(logisticsRecordsRepository.findById(anyInt())).willReturn(Optional.of(logisticsRecords));
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
        given(orderInfoRepository.findByOrderId(anyInt())).willReturn(orderInfoList);
        Product product = new Product();
        product.setId(1);
        product.setCount(10);
        given(productRepository.findById(anyInt())).willReturn(Optional.of(product));
        given(productRepository.save(any())).willReturn(null);
        given(orderInfoRepository.saveAll(any())).willReturn(null);
        given(logisticsRecordsRepository.save(any())).willReturn(logisticsRecords);

        logisticsRecordsService.signed(1);

        verify(orderInfoRepository).findByOrderId(anyInt());
        verify(productRepository, times(3)).findById(any());
        verify(productRepository, times(3)).save(any());
        verify(orderInfoRepository).saveAll(any());
        verify(logisticsRecordsRepository).save(any());
    }
}
