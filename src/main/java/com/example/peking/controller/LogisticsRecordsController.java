package com.example.peking.controller;

import com.example.peking.constant.UriConstants;
import com.example.peking.entity.LogisticsRecords;
import com.example.peking.service.LogisticsRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(UriConstants.LOGISTICS_RECORDS)
public class LogisticsRecordsController {

    @Autowired
    private LogisticsRecordsService logisticsRecordsService;

    @GetMapping(UriConstants.ID)
    HttpEntity<LogisticsRecords> findById(@PathVariable Integer id) {

        LogisticsRecords logisticsRecords = logisticsRecordsService.findById(id);
        if (logisticsRecords == null) {
            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(logisticsRecords, HttpStatus.OK);
    }
}
