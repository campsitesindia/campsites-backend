package com.dd.campsites.campsitesindia.razorpay.payment.model;

import lombok.Data;

@Data
public class OrderResponse {

    private String applicationFee;
    private String razorpayOrderId;
    private String secretKey;
}
