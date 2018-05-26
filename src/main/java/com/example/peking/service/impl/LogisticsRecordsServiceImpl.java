package com.example.peking.service.impl;

import com.example.peking.entity.LogisticsRecords;
import com.example.peking.repository.LogisticsRecordsRepository;
import com.example.peking.service.LogisticsRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogisticsRecordsServiceImpl implements LogisticsRecordsService {

    @Autowired
    private LogisticsRecordsRepository logisticsRecordsRepository;

    @Override
    public LogisticsRecords findById(Integer id) {
        return logisticsRecordsRepository.findById(id).orElse(null);
    }
}
