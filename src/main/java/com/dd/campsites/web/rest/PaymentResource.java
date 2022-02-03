package com.dd.campsites.web.rest;

import com.dd.campsites.campsitesindia.razorpay.config.RazorpayClientWithConfig;
import com.dd.campsites.campsitesindia.razorpay.payment.model.OrderEntity;
import com.dd.campsites.campsitesindia.razorpay.payment.model.OrderResponse;
import com.dd.campsites.campsitesindia.razorpay.payment.model.PaymentResponse;
import com.dd.campsites.campsitesindia.razorpay.util.Signature;
import com.dd.campsites.campsitesindia.util.ApiResponse;
import com.dd.campsites.domain.Bookings;
import com.dd.campsites.domain.User;
import com.dd.campsites.repository.UserRepository;
import com.dd.campsites.service.BookingsService;
import com.dd.campsites.service.campsitesindia.model.ListingModel;
import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import com.razorpay.RazorpayException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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

    private final UserRepository userRepository;

    private final BookingsService bookingsService;

    private RazorpayClient razorpayClient;

    public PaymentResource(
        RazorpayClientWithConfig razorpayClientWithConfig,
        UserRepository userRepository,
        BookingsService bookingsService
    ) throws RazorpayException {
        this.razorpayClientWithConfig = razorpayClientWithConfig;
        this.userRepository = userRepository;
        this.bookingsService = bookingsService;

        razorpayClient = this.razorpayClientWithConfig.client();
    }

    /**
     * {@code GET  /orderId} : get orderId.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */
    @PostMapping("/bookings/order")
    public ResponseEntity<?> createOrder(@RequestBody Bookings bookings) throws RazorpayException {
        log.debug("REST request to save Bookings - Order : {}", bookings);
        User user = null;
        if (getUser().isEmpty()) {
            log.info(" No user is present ................");
            return new ResponseEntity<>(
                new ApiResponse(false, "Error while create payment order: " + "User Not Found"),
                HttpStatus.EXPECTATION_FAILED
            );
        } else {
            user = getUser().get();
        }

        if (user.getId() != bookings.getUser().getId()) {
            log.info(" User Mismatch ................" + bookings.getUser().getId());
            return new ResponseEntity<>(
                new ApiResponse(false, "Error while create payment order: " + "Mismatch"),
                HttpStatus.EXPECTATION_FAILED
            );
        }
        bookings.setCreatedBy("User");
        Bookings savedBooking = bookingsService.save(bookings);
        OrderResponse razorPay = null;
        try {
            // The transaction amount is expressed in the currency subunit, such
            // as paise (in case of INR)
            String amountInPaise = convertRupeeToPaise(bookings.getTotalAmount().toString());
            // Create an order in RazorPay and get the order id
            Order order = createRazorPayOrder(amountInPaise, savedBooking.getId().toString());
            razorPay = getOrderResponse((String) order.get("id"), amountInPaise);
            // Save order in the database
            savedBooking.setRazorpayOrderId(razorPay.getRazorpayOrderId());
            savedBooking.setUpdateDate(Instant.now());
            bookingsService.partialUpdate(savedBooking);
        } catch (RazorpayException e) {
            log.error("Exception while create payment order", e);
            //return ResponseEntity.internalServerError().body("Error while create payment order: " + e.getMessage());
            return new ResponseEntity<>(
                new ApiResponse(false, "Error while create payment order: " + e.getMessage()),
                HttpStatus.EXPECTATION_FAILED
            );
        }
        return ResponseEntity.ok(savedBooking);
    }

    //       // return ResponseEntity.ok(razorPay);
    //        JSONObject options = new JSONObject();
    //        options.put("amount", amount); // Note: The amount should be in paise.
    //        options.put("currency", "INR");
    //        options.put("receipt", "txn_123456");
    //        options.put("payment_capture", 1);
    //
    //        Order order = razorpayClient.Orders.create(options);
    //
    //        OrderEntity orderEntity = new OrderEntity();
    //        orderEntity.setId(order.get("id"));
    //
    //        System.out.println(order);
    //        System.out.println("...........order.........." + order.get("id"));
    //        return ResponseEntity.status(HttpStatus.OK).body(orderEntity);
    //   }

    /**
     * {@code GET  /orderId} : get orderId.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of features in body.
     */

    @PutMapping("/bookings/order")
    public ResponseEntity<?> updateOrder(@RequestBody PaymentResponse paymentResponse) {
        log.debug("REST request to update partial Bookings - Order : {}", paymentResponse.getRazorpayOrderId());
        Bookings bookings = bookingsService.findOneByRazorOrderId(paymentResponse.getRazorpayOrderId());
        Optional<Bookings> updatedBooking = Optional.of(new Bookings());
        String errorMsg = null;
        try {
            bookingsService.findOneByRazorOrderId(paymentResponse.getRazorpayOrderId());
            // Verify if the razorpay signature matches the generated one to
            // confirm the authenticity of the details returned
            String generatedSignature = Signature.calculateRFC2104HMAC(
                bookings.getRazorpayOrderId() + "|" + paymentResponse.getRazorpayPaymentId(),
                razorpayClientWithConfig.getSecret()
            );
            if (
                generatedSignature.equals(paymentResponse.getRazorpaySignature()) &&
                paymentResponse.getRazorpayOrderId().equals(bookings.getRazorpayOrderId())
            ) {
                bookings.setRazorpayPaymentId(paymentResponse.getRazorpayPaymentId());
                bookings.setRazorpaySignature(paymentResponse.getRazorpaySignature());
                bookings.setUpdateDate(Instant.now());
                updatedBooking = bookingsService.partialUpdate(bookings);
            } else {
                errorMsg = "Payment validation failed: Signature doesn't match";
            }
        } catch (Exception e) {
            return new ResponseEntity<>(new ApiResponse(false, e.getMessage()), HttpStatus.BAD_REQUEST);
        }

        if (errorMsg != null) {
            return new ResponseEntity<>(new ApiResponse(false, errorMsg), HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(updatedBooking.get());
    }

    private OrderResponse getOrderResponse(String orderId, String amountInPaise) {
        OrderResponse razorPay = new OrderResponse();
        razorPay.setApplicationFee(amountInPaise);
        razorPay.setRazorpayOrderId(orderId);
        razorPay.setSecretKey(razorpayClientWithConfig.getKey());
        return razorPay;
    }

    private Order createRazorPayOrder(String amount, String tx_bookingId) throws RazorpayException {
        JSONObject options = new JSONObject();
        options.put("amount", amount);
        options.put("currency", "INR");
        options.put("receipt", tx_bookingId);
        return razorpayClient.Orders.create(options);
    }

    private String convertRupeeToPaise(String paise) {
        BigDecimal b = new BigDecimal(paise);
        BigDecimal value = b.multiply(new BigDecimal("100"));
        return value.setScale(0, RoundingMode.UP).toString();
    }

    private Optional<User> getUser() {
        log.info("Getting Auth user...... .....................................{}", getAuthentication().getName());
        Optional<User> user = userRepository.findOneByLogin(getAuthentication().getName());
        log.info("logged in user...... {}", user.get().getEmail());
        return user;
    }

    private Authentication getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("logged in authentication...... {}", authentication.isAuthenticated());
        assert (authentication.isAuthenticated());

        return authentication;
    }
}
