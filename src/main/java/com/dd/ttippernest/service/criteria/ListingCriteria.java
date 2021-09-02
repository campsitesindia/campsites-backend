package com.dd.ttippernest.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.InstantFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.dd.ttippernest.domain.Listing} entity. This class is used
 * in {@link com.dd.ttippernest.web.rest.ListingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /listings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class ListingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter address;

    private DoubleFilter latitude;

    private DoubleFilter longitude;

    private StringFilter url;

    private StringFilter title;

    private StringFilter content;

    private StringFilter thumbnail;

    private BooleanFilter isFeatured;

    private StringFilter phone;

    private StringFilter email;

    private StringFilter website;

    private BooleanFilter comment;

    private BooleanFilter disableBooking;

    private IntegerFilter viewCount;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private InstantFilter updatedBy;

    private InstantFilter updateDate;

    private LongFilter listingTypeId;

    private LongFilter ratingId;

    private LongFilter locationId;

    private LongFilter featureId;

    private LongFilter roomId;

    private LongFilter ownerId;

    public ListingCriteria() {}

    public ListingCriteria(ListingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.address = other.address == null ? null : other.address.copy();
        this.latitude = other.latitude == null ? null : other.latitude.copy();
        this.longitude = other.longitude == null ? null : other.longitude.copy();
        this.url = other.url == null ? null : other.url.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.content = other.content == null ? null : other.content.copy();
        this.thumbnail = other.thumbnail == null ? null : other.thumbnail.copy();
        this.isFeatured = other.isFeatured == null ? null : other.isFeatured.copy();
        this.phone = other.phone == null ? null : other.phone.copy();
        this.email = other.email == null ? null : other.email.copy();
        this.website = other.website == null ? null : other.website.copy();
        this.comment = other.comment == null ? null : other.comment.copy();
        this.disableBooking = other.disableBooking == null ? null : other.disableBooking.copy();
        this.viewCount = other.viewCount == null ? null : other.viewCount.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
        this.listingTypeId = other.listingTypeId == null ? null : other.listingTypeId.copy();
        this.ratingId = other.ratingId == null ? null : other.ratingId.copy();
        this.locationId = other.locationId == null ? null : other.locationId.copy();
        this.featureId = other.featureId == null ? null : other.featureId.copy();
        this.roomId = other.roomId == null ? null : other.roomId.copy();
        this.ownerId = other.ownerId == null ? null : other.ownerId.copy();
    }

    @Override
    public ListingCriteria copy() {
        return new ListingCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public LongFilter id() {
        if (id == null) {
            id = new LongFilter();
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAddress() {
        return address;
    }

    public StringFilter address() {
        if (address == null) {
            address = new StringFilter();
        }
        return address;
    }

    public void setAddress(StringFilter address) {
        this.address = address;
    }

    public DoubleFilter getLatitude() {
        return latitude;
    }

    public DoubleFilter latitude() {
        if (latitude == null) {
            latitude = new DoubleFilter();
        }
        return latitude;
    }

    public void setLatitude(DoubleFilter latitude) {
        this.latitude = latitude;
    }

    public DoubleFilter getLongitude() {
        return longitude;
    }

    public DoubleFilter longitude() {
        if (longitude == null) {
            longitude = new DoubleFilter();
        }
        return longitude;
    }

    public void setLongitude(DoubleFilter longitude) {
        this.longitude = longitude;
    }

    public StringFilter getUrl() {
        return url;
    }

    public StringFilter url() {
        if (url == null) {
            url = new StringFilter();
        }
        return url;
    }

    public void setUrl(StringFilter url) {
        this.url = url;
    }

    public StringFilter getTitle() {
        return title;
    }

    public StringFilter title() {
        if (title == null) {
            title = new StringFilter();
        }
        return title;
    }

    public void setTitle(StringFilter title) {
        this.title = title;
    }

    public StringFilter getContent() {
        return content;
    }

    public StringFilter content() {
        if (content == null) {
            content = new StringFilter();
        }
        return content;
    }

    public void setContent(StringFilter content) {
        this.content = content;
    }

    public StringFilter getThumbnail() {
        return thumbnail;
    }

    public StringFilter thumbnail() {
        if (thumbnail == null) {
            thumbnail = new StringFilter();
        }
        return thumbnail;
    }

    public void setThumbnail(StringFilter thumbnail) {
        this.thumbnail = thumbnail;
    }

    public BooleanFilter getIsFeatured() {
        return isFeatured;
    }

    public BooleanFilter isFeatured() {
        if (isFeatured == null) {
            isFeatured = new BooleanFilter();
        }
        return isFeatured;
    }

    public void setIsFeatured(BooleanFilter isFeatured) {
        this.isFeatured = isFeatured;
    }

    public StringFilter getPhone() {
        return phone;
    }

    public StringFilter phone() {
        if (phone == null) {
            phone = new StringFilter();
        }
        return phone;
    }

    public void setPhone(StringFilter phone) {
        this.phone = phone;
    }

    public StringFilter getEmail() {
        return email;
    }

    public StringFilter email() {
        if (email == null) {
            email = new StringFilter();
        }
        return email;
    }

    public void setEmail(StringFilter email) {
        this.email = email;
    }

    public StringFilter getWebsite() {
        return website;
    }

    public StringFilter website() {
        if (website == null) {
            website = new StringFilter();
        }
        return website;
    }

    public void setWebsite(StringFilter website) {
        this.website = website;
    }

    public BooleanFilter getComment() {
        return comment;
    }

    public BooleanFilter comment() {
        if (comment == null) {
            comment = new BooleanFilter();
        }
        return comment;
    }

    public void setComment(BooleanFilter comment) {
        this.comment = comment;
    }

    public BooleanFilter getDisableBooking() {
        return disableBooking;
    }

    public BooleanFilter disableBooking() {
        if (disableBooking == null) {
            disableBooking = new BooleanFilter();
        }
        return disableBooking;
    }

    public void setDisableBooking(BooleanFilter disableBooking) {
        this.disableBooking = disableBooking;
    }

    public IntegerFilter getViewCount() {
        return viewCount;
    }

    public IntegerFilter viewCount() {
        if (viewCount == null) {
            viewCount = new IntegerFilter();
        }
        return viewCount;
    }

    public void setViewCount(IntegerFilter viewCount) {
        this.viewCount = viewCount;
    }

    public StringFilter getCreatedBy() {
        return createdBy;
    }

    public StringFilter createdBy() {
        if (createdBy == null) {
            createdBy = new StringFilter();
        }
        return createdBy;
    }

    public void setCreatedBy(StringFilter createdBy) {
        this.createdBy = createdBy;
    }

    public InstantFilter getCreatedDate() {
        return createdDate;
    }

    public InstantFilter createdDate() {
        if (createdDate == null) {
            createdDate = new InstantFilter();
        }
        return createdDate;
    }

    public void setCreatedDate(InstantFilter createdDate) {
        this.createdDate = createdDate;
    }

    public InstantFilter getUpdatedBy() {
        return updatedBy;
    }

    public InstantFilter updatedBy() {
        if (updatedBy == null) {
            updatedBy = new InstantFilter();
        }
        return updatedBy;
    }

    public void setUpdatedBy(InstantFilter updatedBy) {
        this.updatedBy = updatedBy;
    }

    public InstantFilter getUpdateDate() {
        return updateDate;
    }

    public InstantFilter updateDate() {
        if (updateDate == null) {
            updateDate = new InstantFilter();
        }
        return updateDate;
    }

    public void setUpdateDate(InstantFilter updateDate) {
        this.updateDate = updateDate;
    }

    public LongFilter getListingTypeId() {
        return listingTypeId;
    }

    public LongFilter listingTypeId() {
        if (listingTypeId == null) {
            listingTypeId = new LongFilter();
        }
        return listingTypeId;
    }

    public void setListingTypeId(LongFilter listingTypeId) {
        this.listingTypeId = listingTypeId;
    }

    public LongFilter getRatingId() {
        return ratingId;
    }

    public LongFilter ratingId() {
        if (ratingId == null) {
            ratingId = new LongFilter();
        }
        return ratingId;
    }

    public void setRatingId(LongFilter ratingId) {
        this.ratingId = ratingId;
    }

    public LongFilter getLocationId() {
        return locationId;
    }

    public LongFilter locationId() {
        if (locationId == null) {
            locationId = new LongFilter();
        }
        return locationId;
    }

    public void setLocationId(LongFilter locationId) {
        this.locationId = locationId;
    }

    public LongFilter getFeatureId() {
        return featureId;
    }

    public LongFilter featureId() {
        if (featureId == null) {
            featureId = new LongFilter();
        }
        return featureId;
    }

    public void setFeatureId(LongFilter featureId) {
        this.featureId = featureId;
    }

    public LongFilter getRoomId() {
        return roomId;
    }

    public LongFilter roomId() {
        if (roomId == null) {
            roomId = new LongFilter();
        }
        return roomId;
    }

    public void setRoomId(LongFilter roomId) {
        this.roomId = roomId;
    }

    public LongFilter getOwnerId() {
        return ownerId;
    }

    public LongFilter ownerId() {
        if (ownerId == null) {
            ownerId = new LongFilter();
        }
        return ownerId;
    }

    public void setOwnerId(LongFilter ownerId) {
        this.ownerId = ownerId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final ListingCriteria that = (ListingCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(address, that.address) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(url, that.url) &&
            Objects.equals(title, that.title) &&
            Objects.equals(content, that.content) &&
            Objects.equals(thumbnail, that.thumbnail) &&
            Objects.equals(isFeatured, that.isFeatured) &&
            Objects.equals(phone, that.phone) &&
            Objects.equals(email, that.email) &&
            Objects.equals(website, that.website) &&
            Objects.equals(comment, that.comment) &&
            Objects.equals(disableBooking, that.disableBooking) &&
            Objects.equals(viewCount, that.viewCount) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updateDate, that.updateDate) &&
            Objects.equals(listingTypeId, that.listingTypeId) &&
            Objects.equals(ratingId, that.ratingId) &&
            Objects.equals(locationId, that.locationId) &&
            Objects.equals(featureId, that.featureId) &&
            Objects.equals(roomId, that.roomId) &&
            Objects.equals(ownerId, that.ownerId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            address,
            latitude,
            longitude,
            url,
            title,
            content,
            thumbnail,
            isFeatured,
            phone,
            email,
            website,
            comment,
            disableBooking,
            viewCount,
            createdBy,
            createdDate,
            updatedBy,
            updateDate,
            listingTypeId,
            ratingId,
            locationId,
            featureId,
            roomId,
            ownerId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ListingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (address != null ? "address=" + address + ", " : "") +
            (latitude != null ? "latitude=" + latitude + ", " : "") +
            (longitude != null ? "longitude=" + longitude + ", " : "") +
            (url != null ? "url=" + url + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (content != null ? "content=" + content + ", " : "") +
            (thumbnail != null ? "thumbnail=" + thumbnail + ", " : "") +
            (isFeatured != null ? "isFeatured=" + isFeatured + ", " : "") +
            (phone != null ? "phone=" + phone + ", " : "") +
            (email != null ? "email=" + email + ", " : "") +
            (website != null ? "website=" + website + ", " : "") +
            (comment != null ? "comment=" + comment + ", " : "") +
            (disableBooking != null ? "disableBooking=" + disableBooking + ", " : "") +
            (viewCount != null ? "viewCount=" + viewCount + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
            (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
            (listingTypeId != null ? "listingTypeId=" + listingTypeId + ", " : "") +
            (ratingId != null ? "ratingId=" + ratingId + ", " : "") +
            (locationId != null ? "locationId=" + locationId + ", " : "") +
            (featureId != null ? "featureId=" + featureId + ", " : "") +
            (roomId != null ? "roomId=" + roomId + ", " : "") +
            (ownerId != null ? "ownerId=" + ownerId + ", " : "") +
            "}";
    }
}
