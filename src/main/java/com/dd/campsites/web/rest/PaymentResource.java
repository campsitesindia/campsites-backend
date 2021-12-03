package com.dd.campsites.web.rest;

import com.dd.campsites.campsitesindia.razorpay.config.RazorpayClientWithConfig;
import com.dd.campsites.campsitesindia.razorpay.payment.model.OrderEntity;
import com.dd.campsites.campsitesindia.razorpay.payment.model.OrderResponse;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing {@link PaymentResource}.
 */
@RestController
@RequestMapping("/api")
public class PaymentResource {

    private final Logger log = LoggerFactory.getLogger(PaymentResource.class);

    private static final String ENTITY_NAME = "payment";

    private int amount = 5000;

    private final RazorpayClientWithConfig razorpayClientWithConfig;

    private RazorpayClient razorpayClient;

    public PaymentResource(RazorpayClientWithConfig razorpayClientWithConfig) throws RazorpayException {
        this.razorpayClientWithConfig = razorpayClientWithConfig;

        razorpayClient = this.razorpayClientWithConfig.client();
    }

    /**
     * {@code GET  /orderId} : get orderId.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @PostMapping("/payment/getPaymentForm")
    public ResponseEntity<OrderEntity> getPaymentForm() throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", amount); // Note: The amount should be in paise.
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        options.put("payment_capture", 1);

        Order order = razorpayClient.Orders.create(options);

        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setId(order.get("id"));

        System.out.println(order);
        System.out.println("...........order.........." + order.get("id"));
        return ResponseEntity.status(HttpStatus.OK).body(orderEntity);
    }

    private OrderResponse getOrderResponse(String orderId, String amountInPaise) {
        OrderResponse razorPay = new OrderResponse();
        razorPay.setApplicationFee(amountInPaise);
        razorPay.setRazorpayOrderId(orderId);
        razorPay.setSecretKey(razorpayClientWithConfig.getKey());
        return razorPay;
    }

    private Order createRazorPayOrder(String amount) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", amount);
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        return razorpayClient.Orders.create(options);
    }

    private String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();
    }
}
