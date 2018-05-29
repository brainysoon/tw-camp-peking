package com.example.peking.service;

import com.example.peking.entity.LogisticsRecords;

public interface LogisticsRecordsService {

    LogisticsRecords findById(Integer id);

    LogisticsRecords shipping(Integer id);

    LogisticsRecords signed(Integer id);
}
