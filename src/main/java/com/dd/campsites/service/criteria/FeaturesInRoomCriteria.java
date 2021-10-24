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
 * Criteria class for the {@link com.dd.campsites.domain.FeaturesInRoom} entity. This class is used
 * in {@link com.dd.campsites.web.rest.FeaturesInRoomResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /features-in-rooms?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FeaturesInRoomCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private LongFilter roomId;

    private LongFilter featureId;

    public FeaturesInRoomCriteria() {}

    public FeaturesInRoomCriteria(FeaturesInRoomCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.roomId = other.roomId == null ? null : other.roomId.copy();
        this.featureId = other.featureId == null ? null : other.featureId.copy();
    }

    @Override
    public FeaturesInRoomCriteria copy() {
        return new FeaturesInRoomCriteria(this);
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FeaturesInRoomCriteria that = (FeaturesInRoomCriteria) o;
        return Objects.equals(id, that.id) && Objects.equals(roomId, that.roomId) && Objects.equals(featureId, that.featureId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, roomId, featureId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeaturesInRoomCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (roomId != null ? "roomId=" + roomId + ", " : "") +
            (featureId != null ? "featureId=" + featureId + ", " : "") +
            "}";
    }
}
