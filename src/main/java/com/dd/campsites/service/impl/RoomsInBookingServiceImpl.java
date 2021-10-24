package com.dd.campsites.service.impl;

import com.dd.campsites.domain.RoomsInBooking;
import com.dd.campsites.repository.RoomsInBookingRepository;
import com.dd.campsites.service.RoomsInBookingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RoomsInBooking}.
 */
@Service
@Transactional
public class RoomsInBookingServiceImpl implements RoomsInBookingService {

    private final Logger log = LoggerFactory.getLogger(RoomsInBookingServiceImpl.class);

    private final RoomsInBookingRepository roomsInBookingRepository;

    public RoomsInBookingServiceImpl(RoomsInBookingRepository roomsInBookingRepository) {
        this.roomsInBookingRepository = roomsInBookingRepository;
    }

    @Override
    public RoomsInBooking save(RoomsInBooking roomsInBooking) {
        log.debug("Request to save RoomsInBooking : {}", roomsInBooking);
        return roomsInBookingRepository.save(roomsInBooking);
    }

    @Override
    public Optional<RoomsInBooking> partialUpdate(RoomsInBooking roomsInBooking) {
        log.debug("Request to partially update RoomsInBooking : {}", roomsInBooking);

        return roomsInBookingRepository
            .findById(roomsInBooking.getId())
            .map(
                existingRoomsInBooking -> {
                    return existingRoomsInBooking;
                }
            )
            .map(roomsInBookingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RoomsInBooking> findAll(Pageable pageable) {
        log.debug("Request to get all RoomsInBookings");
        return roomsInBookingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RoomsInBooking> findOne(Long id) {
        log.debug("Request to get RoomsInBooking : {}", id);
        return roomsInBookingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoomsInBooking : {}", id);
        roomsInBookingRepository.deleteById(id);
    }
}
