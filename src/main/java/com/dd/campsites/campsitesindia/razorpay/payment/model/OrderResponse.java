package com.dd.campsites.campsitesindia.razorpay.payment.model;

import java.util.Objects;

public class OrderResponse {

    private String applicationFee;
    private String razorpayOrderId;
    private String secretKey;

    public String getApplicationFee() {
        return applicationFee;
    }

    public void setApplicationFee(String applicationFee) {
        this.applicationFee = applicationFee;
    }

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderResponse that = (OrderResponse) o;
        return (
            Objects.equals(applicationFee, that.applicationFee) &&
            Objects.equals(razorpayOrderId, that.razorpayOrderId) &&
            Objects.equals(secretKey, that.secretKey)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(applicationFee, razorpayOrderId, secretKey);
    }
}
