package com.dd.ttippernest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Videos.
 */
@Entity
@Table(name = "videos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Videos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "url")
    private String url;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_by")
    private Instant updatedBy;

    @Column(name = "update_date")
    private Instant updateDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "listingType", "rating", "location", "feature", "room", "owner" }, allowSetters = true)
    private Listing listing;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Videos id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Videos name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return this.url;
    }

    public Videos url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Videos createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Videos createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedBy() {
        return this.updatedBy;
    }

    public Videos updatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public Videos updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Listing getListing() {
        return this.listing;
    }

    public Videos listing(Listing listing) {
        this.setListing(listing);
        return this;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Videos)) {
            return false;
        }
        return id != null && id.equals(((Videos) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Videos{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", url='" + getUrl() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
