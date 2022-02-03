package com.dd.campsites.campsitesindia.razorpay.payment.model;

import java.util.Objects;
import lombok.Data;

public class PaymentResponse {

    private String razorpayOrderId;
    private String razorpayPaymentId;
    private String razorpaySignature;

    public String getRazorpayOrderId() {
        return razorpayOrderId;
    }

    public void setRazorpayOrderId(String razorpayOrderId) {
        this.razorpayOrderId = razorpayOrderId;
    }

    public String getRazorpayPaymentId() {
        return razorpayPaymentId;
    }

    public void setRazorpayPaymentId(String razorpayPaymentId) {
        this.razorpayPaymentId = razorpayPaymentId;
    }

    public String getRazorpaySignature() {
        return razorpaySignature;
    }

    public void setRazorpaySignature(String razorpaySignature) {
        this.razorpaySignature = razorpaySignature;
    }

    @Override
    public String toString() {
        return (
            "PaymentResponse{" +
            "razorpayOrderId='" +
            razorpayOrderId +
            '\'' +
            ", razorpayPaymentId='" +
            razorpayPaymentId +
            '\'' +
            ", razorpaySignature='" +
            razorpaySignature +
            '\'' +
            '}'
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PaymentResponse that = (PaymentResponse) o;
        return (
            Objects.equals(razorpayOrderId, that.razorpayOrderId) &&
            Objects.equals(razorpayPaymentId, that.razorpayPaymentId) &&
            Objects.equals(razorpaySignature, that.razorpaySignature)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(razorpayOrderId, razorpayPaymentId, razorpaySignature);
    }
}
