package com.dd.campsites.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.BooleanFilter;
import tech.jhipster.service.filter.DoubleFilter;
import tech.jhipster.service.filter.Filter;
import tech.jhipster.service.filter.FloatFilter;
import tech.jhipster.service.filter.IntegerFilter;
import tech.jhipster.service.filter.LongFilter;
import tech.jhipster.service.filter.StringFilter;

/**
 * Criteria class for the {@link com.dd.campsites.domain.RoomsForListing} entity. This class is used
 * in {@link com.dd.campsites.web.rest.RoomsForListingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rooms-for-listings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RoomsForListingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter listingId;

    private LongFilter roomId;

    public RoomsForListingCriteria() {}

    public RoomsForListingCriteria(RoomsForListingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.listingId = other.listingId == null ? null : other.listingId.copy();
        this.roomId = other.roomId == null ? null : other.roomId.copy();
    }

    @Override
    public RoomsForListingCriteria copy() {
        return new RoomsForListingCriteria(this);
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

    public LongFilter getListingId() {
        return listingId;
    }

    public LongFilter listingId() {
        if (listingId == null) {
            listingId = new LongFilter();
        }
        return listingId;
    }

    public void setListingId(LongFilter listingId) {
        this.listingId = listingId;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RoomsForListingCriteria that = (RoomsForListingCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(listingId, that.listingId) && Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, listingId, roomId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomsForListingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (listingId != null ? "listingId=" + listingId + ", " : "") +
            (roomId != null ? "roomId=" + roomId + ", " : "") +
            "}";
    }
}
