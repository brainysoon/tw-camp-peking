package com.example.peking.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SelectBeforeUpdate;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Data
@SelectBeforeUpdate
@DynamicUpdate
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private String description;

    private Double price;

    private Integer count;

    private Date modifiedTime;

    private Date createTime;

    private Byte status;

    @OneToMany
    @JoinColumn(name = "productId")
    private List<OrderInfo> inventory;
}
