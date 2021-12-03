package com.dd.campsites.config;

import java.util.ArrayList;
import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

/**
 * Properties specific to Campsitesindia.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final OAuth2 oauth2 = new OAuth2();

    //    private PaymentRazorPay paymentRazorPay = new PaymentRazorPay();

    public static final class OAuth2 {

        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }
    }

    //    public static class PaymentRazorPay {
    //
    //        public void setApiKey(String apiKey) {
    //            this.apiKey = apiKey;
    //        }
    //
    //        public void setSecretKey(String secretKey) {
    //            this.secretKey = secretKey;
    //        }
    //
    //        private String apiKey;
    //        private String secretKey;
    //
    //        public String getApiKey() {
    //            return apiKey;
    //        }
    //
    //        public String getSecretKey() {
    //            return secretKey;
    //        }
    //
    //        public PaymentRazorPay paymentRazorPay(String apiKey, String secretKey) {
    //            this.apiKey = apiKey;
    //            this.secretKey = secretKey;
    //            return this;
    //        }
    //
    //        @Override
    //        public String toString() {
    //            return "PaymentRazorPay{" + "apiKey='" + apiKey + '\'' + ", secretKey='" + secretKey + '\'' + '}';
    //        }
    //    }

    public OAuth2 getOauth2() {
        return oauth2;
    }
    //    public void setPaymentRazorPay(PaymentRazorPay paymentRazorPay) {
    //        this.paymentRazorPay = paymentRazorPay;
    //    }
    //
    //    public PaymentRazorPay getPaymentRazorPay() {
    //        return paymentRazorPay;
    //    }
}
