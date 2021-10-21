package com.dd.campsites.service.campsitesindia;

import com.dd.campsites.domain.Features;
import com.dd.campsites.domain.FeaturesListing;
import com.dd.campsites.repository.FeaturesListingRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class FeatureBusinessServiceImpl implements FeatureBusinessService {

    private final FeaturesListingRepository featuresListingRepository;

    public FeatureBusinessServiceImpl(FeaturesListingRepository featuresListingRepository) {
        this.featuresListingRepository = featuresListingRepository;
    }

    @Override
    public List<Features> findByListingId(Long id) {
        // featuresListingRepository.
        List<Features> features = new ArrayList<Features>();
        List<FeaturesListing> fl = featuresListingRepository.findAllByListingIdEquals(id);

        for (FeaturesListing featuresListing : fl) {
            features.add(featuresListing.getFeature());
        }
        return features;
    }
}
