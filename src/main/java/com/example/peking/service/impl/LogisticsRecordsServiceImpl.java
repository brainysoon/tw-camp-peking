package com.example.peking.service.impl;

import com.example.peking.constant.StatusConstants;
import com.example.peking.entity.LogisticsRecords;
import com.example.peking.repository.LogisticsRecordsRepository;
import com.example.peking.service.LogisticsRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class LogisticsRecordsServiceImpl implements LogisticsRecordsService {

    @Autowired
    private LogisticsRecordsRepository logisticsRecordsRepository;

    @Override
    public LogisticsRecords findById(Integer id) {
        return logisticsRecordsRepository.findById(id).orElse(null);
    }

    @Override
    public LogisticsRecords shipping(Integer id) {
        LogisticsRecords logisticsRecords = logisticsRecordsRepository.findById(id).orElse(null);
        if (logisticsRecords == null) return null;

        Date currentTime = new Date();
        logisticsRecords.setModifiedTime(currentTime);
        logisticsRecords.setLogisticsStatus(StatusConstants.LOGISTICS_RECORDS_STATUS_SHIPPING);
        logisticsRecords.setOutboundTime(currentTime);

        return logisticsRecordsRepository.save(logisticsRecords);
    }

    @Override
    public LogisticsRecords signed(Integer id) {
        LogisticsRecords logisticsRecords = logisticsRecordsRepository.findById(id).orElse(null);
        if (logisticsRecords == null) return null;

        Date currentTime = new Date();
        logisticsRecords.setModifiedTime(currentTime);
        logisticsRecords.setLogisticsStatus(StatusConstants.LOGISTICS_RECORDS_STATUS_SIGNED);
        logisticsRecords.setSignedTime(currentTime);

        return logisticsRecordsRepository.save(logisticsRecords);
    }
}
