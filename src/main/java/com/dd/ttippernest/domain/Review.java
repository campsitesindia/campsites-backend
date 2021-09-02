package com.dd.ttippernest.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Review.
 */
@Entity
@Table(name = "review")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Review implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "rating")
    private Integer rating;

    @Lob
    @Column(name = "reviewb_body")
    private byte[] reviewbBody;

    @Column(name = "reviewb_body_content_type")
    private String reviewbBodyContentType;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_by")
    private Instant updatedBy;

    @Column(name = "update_date")
    private Instant updateDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "room", "listing", "invoices" }, allowSetters = true)
    private Bookings booking;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Review id(Long id) {
        this.id = id;
        return this;
    }

    public Integer getRating() {
        return this.rating;
    }

    public Review rating(Integer rating) {
        this.rating = rating;
        return this;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public byte[] getReviewbBody() {
        return this.reviewbBody;
    }

    public Review reviewbBody(byte[] reviewbBody) {
        this.reviewbBody = reviewbBody;
        return this;
    }

    public void setReviewbBody(byte[] reviewbBody) {
        this.reviewbBody = reviewbBody;
    }

    public String getReviewbBodyContentType() {
        return this.reviewbBodyContentType;
    }

    public Review reviewbBodyContentType(String reviewbBodyContentType) {
        this.reviewbBodyContentType = reviewbBodyContentType;
        return this;
    }

    public void setReviewbBodyContentType(String reviewbBodyContentType) {
        this.reviewbBodyContentType = reviewbBodyContentType;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Review createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Review createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedBy() {
        return this.updatedBy;
    }

    public Review updatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public Review updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Bookings getBooking() {
        return this.booking;
    }

    public Review booking(Bookings bookings) {
        this.setBooking(bookings);
        return this;
    }

    public void setBooking(Bookings bookings) {
        this.booking = bookings;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Review)) {
            return false;
        }
        return id != null && id.equals(((Review) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Review{" +
            "id=" + getId() +
            ", rating=" + getRating() +
            ", reviewbBody='" + getReviewbBody() + "'" +
            ", reviewbBodyContentType='" + getReviewbBodyContentType() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
