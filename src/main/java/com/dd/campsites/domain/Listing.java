package com.dd.campsites.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;

/**
 * A Listing.
 */
@Entity
@Table(name = "listing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Listing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "url")
    private String url;

    @Column(name = "title")
    private String title;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "content")
    private String content;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "is_featured")
    private Boolean isFeatured;

    @Column(name = "price_per_person")
    private Double pricePerPerson;

    @Column(name = "price_per_child")
    private Double pricePerChild;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "is_published")
    private Boolean isPublished;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Lob
    @Type(type = "org.hibernate.type.TextType")
    @Column(name = "comment")
    private String comment;

    @Column(name = "disable_booking")
    private Boolean disableBooking;

    @Column(name = "view_count")
    private Integer viewCount;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_by")
    private Instant updatedBy;

    @Column(name = "update_date")
    private Instant updateDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private Location location;

    @ManyToOne
    @JsonIgnoreProperties(value = { "parent" }, allowSetters = true)
    private ListingType listingType;

    @ManyToOne
    private User owner;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Listing id(Long id) {
        this.id = id;
        return this;
    }

    public String getAddress() {
        return this.address;
    }

    public Listing address(String address) {
        this.address = address;
        return this;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Listing latitude(Double latitude) {
        this.latitude = latitude;
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Listing longitude(Double longitude) {
        this.longitude = longitude;
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getUrl() {
        return this.url;
    }

    public Listing url(String url) {
        this.url = url;
        return this;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return this.title;
    }

    public Listing title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return this.content;
    }

    public Listing content(String content) {
        this.content = content;
        return this;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public Listing thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public Boolean getIsFeatured() {
        return this.isFeatured;
    }

    public Listing isFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
        return this;
    }

    public void setIsFeatured(Boolean isFeatured) {
        this.isFeatured = isFeatured;
    }

    public Double getPricePerPerson() {
        return this.pricePerPerson;
    }

    public Listing pricePerPerson(Double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
        return this;
    }

    public void setPricePerPerson(Double pricePerPerson) {
        this.pricePerPerson = pricePerPerson;
    }

    public Double getPricePerChild() {
        return this.pricePerChild;
    }

    public Listing pricePerChild(Double pricePerChild) {
        this.pricePerChild = pricePerChild;
        return this;
    }

    public void setPricePerChild(Double pricePerChild) {
        this.pricePerChild = pricePerChild;
    }

    public Double getDiscount() {
        return this.discount;
    }

    public Listing discount(Double discount) {
        this.discount = discount;
        return this;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Boolean getIsPublished() {
        return this.isPublished;
    }

    public Listing isPublished(Boolean isPublished) {
        this.isPublished = isPublished;
        return this;
    }

    public void setIsPublished(Boolean isPublished) {
        this.isPublished = isPublished;
    }

    public String getPhone() {
        return this.phone;
    }

    public Listing phone(String phone) {
        this.phone = phone;
        return this;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return this.email;
    }

    public Listing email(String email) {
        this.email = email;
        return this;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getWebsite() {
        return this.website;
    }

    public Listing website(String website) {
        this.website = website;
        return this;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getComment() {
        return this.comment;
    }

    public Listing comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getDisableBooking() {
        return this.disableBooking;
    }

    public Listing disableBooking(Boolean disableBooking) {
        this.disableBooking = disableBooking;
        return this;
    }

    public void setDisableBooking(Boolean disableBooking) {
        this.disableBooking = disableBooking;
    }

    public Integer getViewCount() {
        return this.viewCount;
    }

    public Listing viewCount(Integer viewCount) {
        this.viewCount = viewCount;
        return this;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Listing createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Listing createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedBy() {
        return this.updatedBy;
    }

    public Listing updatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public Listing updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Location getLocation() {
        return this.location;
    }

    public Listing location(Location location) {
        this.setLocation(location);
        return this;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public ListingType getListingType() {
        return this.listingType;
    }

    public Listing listingType(ListingType listingType) {
        this.setListingType(listingType);
        return this;
    }

    public void setListingType(ListingType listingType) {
        this.listingType = listingType;
    }

    public User getOwner() {
        return this.owner;
    }

    public Listing owner(User user) {
        this.setOwner(user);
        return this;
    }

    public void setOwner(User user) {
        this.owner = user;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Listing)) {
            return false;
        }
        return id != null && id.equals(((Listing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Listing{" +
            "id=" + getId() +
            ", address='" + getAddress() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            ", url='" + getUrl() + "'" +
            ", title='" + getTitle() + "'" +
            ", content='" + getContent() + "'" +
            ", thumbnail='" + getThumbnail() + "'" +
            ", isFeatured='" + getIsFeatured() + "'" +
            ", pricePerPerson=" + getPricePerPerson() +
            ", pricePerChild=" + getPricePerChild() +
            ", discount=" + getDiscount() +
            ", isPublished='" + getIsPublished() + "'" +
            ", phone='" + getPhone() + "'" +
            ", email='" + getEmail() + "'" +
            ", website='" + getWebsite() + "'" +
            ", comment='" + getComment() + "'" +
            ", disableBooking='" + getDisableBooking() + "'" +
            ", viewCount=" + getViewCount() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
