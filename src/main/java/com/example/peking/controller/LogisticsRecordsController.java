package com.example.peking.controller;

import com.example.peking.constant.UriConstants;
import com.example.peking.entity.LogisticsRecords;
import com.example.peking.service.LogisticsRecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(UriConstants.LOGISTICS_RECORDS)
public class LogisticsRecordsController {

    private static final String SHIPPING = "shipping";
    private static final String SIGNED = "signed";

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

    @PutMapping(UriConstants.ID + UriConstants.ORDERS + UriConstants.ORDER_ID)
    HttpEntity<LogisticsRecords> update(@PathVariable Integer id, @RequestParam String logisticsStatus) {

        if (SHIPPING.equals(logisticsStatus)) {

            LogisticsRecords logisticsRecords = logisticsRecordsService.shipping(id);
            if (logisticsRecords == null) {
                return ResponseEntity.noContent().build();
            }
            return new ResponseEntity<>(logisticsRecords, HttpStatus.OK);
        } else if (SIGNED.equals(logisticsStatus)) {

            LogisticsRecords logisticsRecords = logisticsRecordsService.signed(id);
            if (logisticsRecords == null) {
                return ResponseEntity.noContent().build();
            }

            return new ResponseEntity<>(logisticsRecords, HttpStatus.OK);
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
