package com.dd.campsites.service.impl;

import com.dd.campsites.campsitesindia.razorpay.config.RazorpayClientWithConfig;
import com.dd.campsites.campsitesindia.razorpay.util.Signature;
import com.dd.campsites.domain.Bookings;
import com.dd.campsites.repository.BookingsRepository;
import com.dd.campsites.service.BookingsService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Bookings}.
 */
@Service
@Transactional
public class BookingsServiceImpl implements BookingsService {

    private final Logger log = LoggerFactory.getLogger(BookingsServiceImpl.class);

    private final BookingsRepository bookingsRepository;

    private RazorpayClientWithConfig razorpayClientWithConfig;

    public BookingsServiceImpl(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    @Override
    public Bookings save(Bookings bookings) {
        log.debug("Request to save Bookings : {}", bookings);
        return bookingsRepository.save(bookings);
    }

    @Override
    public Optional<Bookings> partialUpdate(Bookings bookings) {
        log.debug("Request to partially update Bookings : {}", bookings);

        return bookingsRepository
            .findById(bookings.getId())
            .map(
                existingBookings -> {
                    if (
                        validateAndUpdateOrder(
                            bookings.getRazorpayOrderId(),
                            bookings.getRazorpayPaymentId(),
                            bookings.getRazorpaySignature(),
                            razorpayClientWithConfig.getSecret()
                        )
                    ) {
                        if (bookings.getName() != null) {
                            existingBookings.setName(bookings.getName());
                        }

                        if (bookings.getCheckInDate() != null) {
                            existingBookings.setCheckInDate(bookings.getCheckInDate());
                        }
                        if (bookings.getCheckOutDate() != null) {
                            existingBookings.setCheckOutDate(bookings.getCheckOutDate());
                        }
                        if (bookings.getPricePerNight() != null) {
                            existingBookings.setPricePerNight(bookings.getPricePerNight());
                        }
                        if (bookings.getChildPricePerNight() != null) {
                            existingBookings.setChildPricePerNight(bookings.getChildPricePerNight());
                        }
                        if (bookings.getNumOfNights() != null) {
                            existingBookings.setNumOfNights(bookings.getNumOfNights());
                        }
                        if (bookings.getRazorpayPaymentId() != null) {
                            existingBookings.setRazorpayPaymentId(bookings.getRazorpayPaymentId());
                        }
                        if (bookings.getRazorpayOrderId() != null) {
                            existingBookings.setRazorpayOrderId(bookings.getRazorpayOrderId());
                        }
                        if (bookings.getRazorpaySignature() != null) {
                            existingBookings.setRazorpaySignature(bookings.getRazorpaySignature());
                        }
                        if (bookings.getDiscount() != null) {
                            existingBookings.setDiscount(bookings.getDiscount());
                        }
                        if (bookings.getTotalAmount() != null) {
                            existingBookings.setTotalAmount(bookings.getTotalAmount());
                        }
                        if (bookings.getCreatedBy() != null) {
                            existingBookings.setCreatedBy(bookings.getCreatedBy());
                        }
                        if (bookings.getCreatedDate() != null) {
                            existingBookings.setCreatedDate(bookings.getCreatedDate());
                        }
                        if (bookings.getUpdatedBy() != null) {
                            existingBookings.setUpdatedBy(bookings.getUpdatedBy());
                        }
                        if (bookings.getUpdateDate() != null) {
                            existingBookings.setUpdateDate(bookings.getUpdateDate());
                        }
                    }

                    return existingBookings;
                }
            )
            .map(bookingsRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Bookings> findAll(Pageable pageable) {
        log.debug("Request to get all Bookings");
        return bookingsRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Bookings> findOne(Long id) {
        log.debug("Request to get Bookings : {}", id);
        return bookingsRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Bookings : {}", id);
        bookingsRepository.deleteById(id);
    }

    private boolean validateAndUpdateOrder(
        final String razorpayOrderId,
        final String razorpayPaymentId,
        final String razorpaySignature,
        final String secret
    ) {
        String errorMsg = null;
        try {
            Bookings bookings = bookingsRepository.findBookingsByRazorpayOrderId(razorpayOrderId);
            // Verify if the razorpay signature matches the generated one to
            // confirm the authenticity of the details returned
            String generatedSignature = Signature.calculateRFC2104HMAC(bookings.getRazorpayOrderId() + "|" + razorpayPaymentId, secret);
            if (generatedSignature.equals(razorpaySignature)) {
                return true;
            } else {
                errorMsg = "Payment validation failed: Signature doesn't match";
            }
        } catch (Exception e) {
            log.error("Payment validation failed", e);
            errorMsg = e.getMessage();
        }
        return false;
    }
}
