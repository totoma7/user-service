package com.example.userservice;


import lombok.Data;

import java.util.Date;

@Data
public class  {
    private String productId;
    private Integer qty;
    private Integer unitPrice;
    private Integer totalPrice;
    private Date createAt;
    private String orderId;
}
