package com.dd.campsites.service.campsitesindia.model;

import com.dd.campsites.domain.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.List;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

public class ListingModel {

    private Listing listing;
    private List<Features> featuresList;
    private List<Photos> photosList;
    private List<Review> reviews;
    private Rating ratings;

    public List<Features> getFeaturesList() {
        return featuresList;
    }

    public void setFeaturesList(List<Features> featuresList) {
        this.featuresList = featuresList;
    }

    public List<Photos> getPhotosList() {
        return photosList;
    }

    public void setPhotosList(List<Photos> photosList) {
        this.photosList = photosList;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public Rating getRatings() {
        return ratings;
    }

    public void setRatings(Rating ratings) {
        this.ratings = ratings;
    }

    public Listing getListing() {
        return listing;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    @Override
    public String toString() {
        return (
            "ListingModel{" +
            "listing=" +
            listing +
            ", featuresList=" +
            featuresList +
            ", photosList=" +
            photosList +
            ", reviews=" +
            reviews +
            ", ratings=" +
            ratings +
            '}'
        );
    }
}
