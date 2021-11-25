package com.dd.campsites.web.rest;

import com.dd.campsites.service.campsitesindia.fileupload.message.ResponseMessage;
import com.dd.campsites.service.campsitesindia.fileupload.service.FilesStorageService;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * REST controller for managing {@link PaymentResource}.
 */
@RestController
@RequestMapping("/api")
public class PaymentResource {

    private final Logger log = LoggerFactory.getLogger(PaymentResource.class);

    private static final String ENTITY_NAME = "payment";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private RazorpayClient client;
    private int amount = 500;

    @Value("${application.paymentrazorpay.apikey}")
    private String apiKey;

    @Value("${application.paymentrazorpay.secretkey}")
    private String secretKey;

    public PaymentResource() {
        try {
            System.out.println("........" + apiKey + "....." + secretKey);
            this.client = new RazorpayClient("rzp_test_pvJmuk3mYLgwDy", "YZfB3casYnq2A1Ad5kFBoc4k");
        } catch (RazorpayException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * {@code GET  /orderId} : get orderId.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @PostMapping("/payment/getPaymentForm")
    public ResponseEntity<String> getPaymentForm() throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", amount); // Note: The amount should be in paise.
        options.put("currency", "INR");
        options.put("receipt", "txn_123456");
        options.put("payment_capture", 1);
        System.out.println("........" + apiKey + "....." + secretKey);

        Order order = client.Orders.create(options);

        return ResponseEntity.status(HttpStatus.OK).body((String) order.get("id"));
    }
}
