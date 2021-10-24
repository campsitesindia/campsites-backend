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
 * Criteria class for the {@link com.dd.campsites.domain.RoomsInBooking} entity. This class is used
 * in {@link com.dd.campsites.web.rest.RoomsInBookingResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /rooms-in-bookings?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RoomsInBookingCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter bookingsId;

    private LongFilter roomId;

    public RoomsInBookingCriteria() {}

    public RoomsInBookingCriteria(RoomsInBookingCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.bookingsId = other.bookingsId == null ? null : other.bookingsId.copy();
        this.roomId = other.roomId == null ? null : other.roomId.copy();
    }

    @Override
    public RoomsInBookingCriteria copy() {
        return new RoomsInBookingCriteria(this);
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

    public LongFilter getBookingsId() {
        return bookingsId;
    }

    public LongFilter bookingsId() {
        if (bookingsId == null) {
            bookingsId = new LongFilter();
        }
        return bookingsId;
    }

    public void setBookingsId(LongFilter bookingsId) {
        this.bookingsId = bookingsId;
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
        final RoomsInBookingCriteria that = (RoomsInBookingCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(bookingsId, that.bookingsId) && Objects.equals(roomId, that.roomId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, bookingsId, roomId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomsInBookingCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (bookingsId != null ? "bookingsId=" + bookingsId + ", " : "") +
            (roomId != null ? "roomId=" + roomId + ", " : "") +
            "}";
    }
}
