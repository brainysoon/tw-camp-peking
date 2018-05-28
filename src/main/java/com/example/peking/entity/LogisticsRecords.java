package com.example.peking.entity;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

@Entity
@Data
public class LogisticsRecords {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer orderId;
    private String deliveryMan;
    private Date outboundTime;
    private Date signedTime;
    private Date modifiedTime;
    private Date createTime;
    private Byte logisticsStatus;
}
