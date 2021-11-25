package com.dd.campsites.campsitesindia.razorpay.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "razorpay")
public class RazorPayClientConfig {

    private String key;
    private String secret;
}
