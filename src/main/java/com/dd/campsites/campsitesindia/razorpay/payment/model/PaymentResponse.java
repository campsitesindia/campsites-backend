package com.dd.campsites.campsitesindia.razorpay.payment.model;

import lombok.Data;

@Data
public class PaymentResponse {

    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;
}
