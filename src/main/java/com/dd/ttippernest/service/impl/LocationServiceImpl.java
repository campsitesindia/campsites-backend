package com.dd.ttippernest.service.impl;

import com.dd.ttippernest.domain.Location;
import com.dd.ttippernest.repository.LocationRepository;
import com.dd.ttippernest.service.LocationService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Location}.
 */
@Service
@Transactional
public class LocationServiceImpl implements LocationService {

    private final Logger log = LoggerFactory.getLogger(LocationServiceImpl.class);

    private final LocationRepository locationRepository;

    public LocationServiceImpl(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    @Override
    public Location save(Location location) {
        log.debug("Request to save Location : {}", location);
        return locationRepository.save(location);
    }

    @Override
    public Optional<Location> partialUpdate(Location location) {
        log.debug("Request to partially update Location : {}", location);

        return locationRepository
            .findById(location.getId())
            .map(
                existingLocation -> {
                    if (location.getTitle() != null) {
                        existingLocation.setTitle(location.getTitle());
                    }
                    if (location.getCount() != null) {
                        existingLocation.setCount(location.getCount());
                    }
                    if (location.getThumbnail() != null) {
                        existingLocation.setThumbnail(location.getThumbnail());
                    }
                    if (location.getIcon() != null) {
                        existingLocation.setIcon(location.getIcon());
                    }
                    if (location.getColor() != null) {
                        existingLocation.setColor(location.getColor());
                    }
                    if (location.getImgIcon() != null) {
                        existingLocation.setImgIcon(location.getImgIcon());
                    }
                    if (location.getDescription() != null) {
                        existingLocation.setDescription(location.getDescription());
                    }
                    if (location.getParent() != null) {
                        existingLocation.setParent(location.getParent());
                    }
                    if (location.getTaxonomy() != null) {
                        existingLocation.setTaxonomy(location.getTaxonomy());
                    }
                    if (location.getCreatedBy() != null) {
                        existingLocation.setCreatedBy(location.getCreatedBy());
                    }
                    if (location.getCreatedDate() != null) {
                        existingLocation.setCreatedDate(location.getCreatedDate());
                    }
                    if (location.getUpdatedBy() != null) {
                        existingLocation.setUpdatedBy(location.getUpdatedBy());
                    }
                    if (location.getUpdateDate() != null) {
                        existingLocation.setUpdateDate(location.getUpdateDate());
                    }

                    return existingLocation;
                }
            )
            .map(locationRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Location> findAll(Pageable pageable) {
        log.debug("Request to get all Locations");
        return locationRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Location> findOne(Long id) {
        log.debug("Request to get Location : {}", id);
        return locationRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Location : {}", id);
        locationRepository.deleteById(id);
    }
}
