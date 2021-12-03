package com.dd.campsites.campsitesindia.razorpay.config;

import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Configuration
public class RazorpayClientWithConfig {

    private static final Logger logger = LoggerFactory.getLogger(RazorpayClientWithConfig.class);

    @Value("${razorpay.apikey}")
    private String key;

    @Value("${razorpay.secretKey}")
    private String secret;

    public String getKey() {
        return key;
    }

    public String getSecret() {
        return secret;
    }

    public RazorpayClient client() throws RazorpayException {
        logger.info("Razor key " + key);

        return new RazorpayClient(key, secret);
    }
}
