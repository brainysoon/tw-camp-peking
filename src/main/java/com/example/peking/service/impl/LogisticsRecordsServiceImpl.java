package com.example.peking.service.impl;

import com.example.peking.constant.StatusConstants;
import com.example.peking.entity.LogisticsRecords;
import com.example.peking.entity.OrderInfo;
import com.example.peking.entity.Product;
import com.example.peking.exception.BusinessException;
import com.example.peking.repository.LogisticsRecordsRepository;
import com.example.peking.repository.OrderInfoRepository;
import com.example.peking.repository.ProductRepository;
import com.example.peking.service.LogisticsRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class LogisticsRecordsServiceImpl implements LogisticsRecordsService {

    private static final String PRODUCT_COUNT_NOT_ENOUGH = "product count not enough";
    private static final String NO_SUCH_PRODUCT = "no such product";
    public static final String NO_SUCH_LOGISTICS = "no such logistics";

    @Autowired
    private LogisticsRecordsRepository logisticsRecordsRepository;

    @Autowired
    private OrderInfoRepository orderInfoRepository;

    @Autowired
    private ProductRepository productRepository;

    @Override
    public LogisticsRecords findById(Integer id) {
        return logisticsRecordsRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NO_SUCH_LOGISTICS, HttpStatus.NO_CONTENT));
    }

    @Override
    public LogisticsRecords shipping(Integer id) {
        LogisticsRecords logisticsRecords = logisticsRecordsRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NO_SUCH_LOGISTICS, HttpStatus.NO_CONTENT));
        Date currentTime = new Date();
        logisticsRecords.setModifiedTime(currentTime);
        logisticsRecords.setLogisticsStatus(StatusConstants.LOGISTICS_RECORDS_STATUS_SHIPPING);
        logisticsRecords.setOutboundTime(currentTime);

        return logisticsRecordsRepository.save(logisticsRecords);
    }

    @Override
    public LogisticsRecords signed(Integer id) {
        LogisticsRecords logisticsRecords = logisticsRecordsRepository.findById(id)
                .orElseThrow(() -> new BusinessException(NO_SUCH_LOGISTICS, HttpStatus.NO_CONTENT));

        Date currentTime = new Date();
        logisticsRecords.setModifiedTime(currentTime);
        logisticsRecords.setLogisticsStatus(StatusConstants.LOGISTICS_RECORDS_STATUS_SIGNED);
        logisticsRecords.setSignedTime(currentTime);

        List<OrderInfo> orderInfoList = orderInfoRepository.findByOrderId(logisticsRecords.getOrderId());
        for (OrderInfo orderInfo : orderInfoList) {
            Product product = productRepository.findById(orderInfo.getProductId())
                    .orElseThrow(() -> new BusinessException(NO_SUCH_PRODUCT, HttpStatus.NO_CONTENT));

            if (product.getCount() < orderInfo.getPurchaseCount()) {
                throw new BusinessException(PRODUCT_COUNT_NOT_ENOUGH, HttpStatus.NOT_ACCEPTABLE);
            }
            product.setCount(product.getCount() - orderInfo.getPurchaseCount());
            productRepository.save(product);

            orderInfo.setModifiedTime(new Date());
            orderInfo.setStatus(StatusConstants.ORDER_INFO_PRODUCT_SIGNED);
        }
        orderInfoRepository.saveAll(orderInfoList);

        return logisticsRecordsRepository.save(logisticsRecords);
    }
}
