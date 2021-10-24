package com.dd.campsites.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FeaturesListing.
 */
@Entity
@Table(name = "features_listing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FeaturesListing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "listingType", "location", "owner" }, allowSetters = true)
    private Listing listing;

    @ManyToOne
    private Features feature;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FeaturesListing id(Long id) {
        this.id = id;
        return this;
    }

    public Listing getListing() {
        return this.listing;
    }

    public FeaturesListing listing(Listing listing) {
        this.setListing(listing);
        return this;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public Features getFeature() {
        return this.feature;
    }

    public FeaturesListing feature(Features features) {
        this.setFeature(features);
        return this;
    }

    public void setFeature(Features features) {
        this.feature = features;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeaturesListing)) {
            return false;
        }
        return id != null && id.equals(((FeaturesListing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeaturesListing{" +
            "id=" + getId() +
            "}";
    }
}
