package com.example.peking.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@Table(name = "`Order`")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer userId;
    private Double totalPrice;
    private Date modifiedTime;
    private Date createTime;
    private Byte status;

    @OneToMany
    @JoinColumn(name = "orderId")
    private List<OrderInfo> purchaseItemList;
}