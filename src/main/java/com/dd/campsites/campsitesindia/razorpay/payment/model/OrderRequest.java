package com.dd.campsites.campsitesindia.razorpay.payment.model;

import java.util.Objects;

public class OrderRequest {

    private String customerName;
    private String email;
    private String phoneNumber;
    private String amount;

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderRequest that = (OrderRequest) o;
        return (
            Objects.equals(customerName, that.customerName) &&
            Objects.equals(email, that.email) &&
            Objects.equals(phoneNumber, that.phoneNumber) &&
            Objects.equals(amount, that.amount)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(customerName, email, phoneNumber, amount);
    }
}
