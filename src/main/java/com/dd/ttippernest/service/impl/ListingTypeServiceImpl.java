package com.dd.ttippernest.service.impl;

import com.dd.ttippernest.domain.ListingType;
import com.dd.ttippernest.repository.ListingTypeRepository;
import com.dd.ttippernest.service.ListingTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link ListingType}.
 */
@Service
@Transactional
public class ListingTypeServiceImpl implements ListingTypeService {

    private final Logger log = LoggerFactory.getLogger(ListingTypeServiceImpl.class);

    private final ListingTypeRepository listingTypeRepository;

    public ListingTypeServiceImpl(ListingTypeRepository listingTypeRepository) {
        this.listingTypeRepository = listingTypeRepository;
    }

    @Override
    public ListingType save(ListingType listingType) {
        log.debug("Request to save ListingType : {}", listingType);
        return listingTypeRepository.save(listingType);
    }

    @Override
    public Optional<ListingType> partialUpdate(ListingType listingType) {
        log.debug("Request to partially update ListingType : {}", listingType);

        return listingTypeRepository
            .findById(listingType.getId())
            .map(
                existingListingType -> {
                    if (listingType.getTitle() != null) {
                        existingListingType.setTitle(listingType.getTitle());
                    }
                    if (listingType.getCount() != null) {
                        existingListingType.setCount(listingType.getCount());
                    }
                    if (listingType.getThumbnail() != null) {
                        existingListingType.setThumbnail(listingType.getThumbnail());
                    }
                    if (listingType.getIcon() != null) {
                        existingListingType.setIcon(listingType.getIcon());
                    }
                    if (listingType.getColor() != null) {
                        existingListingType.setColor(listingType.getColor());
                    }
                    if (listingType.getImgIcon() != null) {
                        existingListingType.setImgIcon(listingType.getImgIcon());
                    }
                    if (listingType.getDescription() != null) {
                        existingListingType.setDescription(listingType.getDescription());
                    }
                    if (listingType.getParent() != null) {
                        existingListingType.setParent(listingType.getParent());
                    }
                    if (listingType.getTaxonomy() != null) {
                        existingListingType.setTaxonomy(listingType.getTaxonomy());
                    }
                    if (listingType.getCreatedBy() != null) {
                        existingListingType.setCreatedBy(listingType.getCreatedBy());
                    }
                    if (listingType.getCreatedDate() != null) {
                        existingListingType.setCreatedDate(listingType.getCreatedDate());
                    }
                    if (listingType.getUpdatedBy() != null) {
                        existingListingType.setUpdatedBy(listingType.getUpdatedBy());
                    }
                    if (listingType.getUpdateDate() != null) {
                        existingListingType.setUpdateDate(listingType.getUpdateDate());
                    }

                    return existingListingType;
                }
            )
            .map(listingTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ListingType> findAll(Pageable pageable) {
        log.debug("Request to get all ListingTypes");
        return listingTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ListingType> findOne(Long id) {
        log.debug("Request to get ListingType : {}", id);
        return listingTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete ListingType : {}", id);
        listingTypeRepository.deleteById(id);
    }
}
