package com.dd.ttippernest.service.impl;

import com.dd.ttippernest.domain.Features;
import com.dd.ttippernest.repository.FeaturesRepository;
import com.dd.ttippernest.service.FeaturesService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Features}.
 */
@Service
@Transactional
public class FeaturesServiceImpl implements FeaturesService {

    private final Logger log = LoggerFactory.getLogger(FeaturesServiceImpl.class);

    private final FeaturesRepository featuresRepository;

    public FeaturesServiceImpl(FeaturesRepository featuresRepository) {
        this.featuresRepository = featuresRepository;
    }

    @Override
    public Features save(Features features) {
        log.debug("Request to save Features : {}", features);
        return featuresRepository.save(features);
    }

    @Override
    public Optional<Features> partialUpdate(Features features) {
        log.debug("Request to partially update Features : {}", features);

        return featuresRepository
            .findById(features.getId())
            .map(
                existingFeatures -> {
                    if (features.getTitle() != null) {
                        existingFeatures.setTitle(features.getTitle());
                    }
                    if (features.getCount() != null) {
                        existingFeatures.setCount(features.getCount());
                    }
                    if (features.getThumbnail() != null) {
                        existingFeatures.setThumbnail(features.getThumbnail());
                    }
                    if (features.getIcon() != null) {
                        existingFeatures.setIcon(features.getIcon());
                    }
                    if (features.getColor() != null) {
                        existingFeatures.setColor(features.getColor());
                    }
                    if (features.getImgIcon() != null) {
                        existingFeatures.setImgIcon(features.getImgIcon());
                    }
                    if (features.getDescription() != null) {
                        existingFeatures.setDescription(features.getDescription());
                    }
                    if (features.getParent() != null) {
                        existingFeatures.setParent(features.getParent());
                    }
                    if (features.getTaxonomy() != null) {
                        existingFeatures.setTaxonomy(features.getTaxonomy());
                    }
                    if (features.getCreatedBy() != null) {
                        existingFeatures.setCreatedBy(features.getCreatedBy());
                    }
                    if (features.getCreatedDate() != null) {
                        existingFeatures.setCreatedDate(features.getCreatedDate());
                    }
                    if (features.getUpdatedBy() != null) {
                        existingFeatures.setUpdatedBy(features.getUpdatedBy());
                    }
                    if (features.getUpdateDate() != null) {
                        existingFeatures.setUpdateDate(features.getUpdateDate());
                    }

                    return existingFeatures;
                }
            )
            .map(featuresRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Features> findAll(Pageable pageable) {
        log.debug("Request to get all Features");
        return featuresRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Features> findOne(Long id) {
        log.debug("Request to get Features : {}", id);
        return featuresRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Features : {}", id);
        featuresRepository.deleteById(id);
    }
}
