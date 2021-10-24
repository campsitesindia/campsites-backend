package com.dd.campsites.service.criteria;

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
 * Criteria class for the {@link com.dd.campsites.domain.RoomType} entity. This class is used
 * in {@link com.dd.campsites.web.rest.RoomTypeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /room-types?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class RoomTypeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter type;

    private StringFilter description;

    private StringFilter maxCapacity;

    private IntegerFilter numberOfBeds;

    private IntegerFilter numberOfBathrooms;

    private DoubleFilter roomRatePerNight;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private InstantFilter updatedBy;

    private InstantFilter updateDate;

    public RoomTypeCriteria() {}

    public RoomTypeCriteria(RoomTypeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.type = other.type == null ? null : other.type.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.maxCapacity = other.maxCapacity == null ? null : other.maxCapacity.copy();
        this.numberOfBeds = other.numberOfBeds == null ? null : other.numberOfBeds.copy();
        this.numberOfBathrooms = other.numberOfBathrooms == null ? null : other.numberOfBathrooms.copy();
        this.roomRatePerNight = other.roomRatePerNight == null ? null : other.roomRatePerNight.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
    }

    @Override
    public RoomTypeCriteria copy() {
        return new RoomTypeCriteria(this);
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

    public StringFilter getType() {
        return type;
    }

    public StringFilter type() {
        if (type == null) {
            type = new StringFilter();
        }
        return type;
    }

    public void setType(StringFilter type) {
        this.type = type;
    }

    public StringFilter getDescription() {
        return description;
    }

    public StringFilter description() {
        if (description == null) {
            description = new StringFilter();
        }
        return description;
    }

    public void setDescription(StringFilter description) {
        this.description = description;
    }

    public StringFilter getMaxCapacity() {
        return maxCapacity;
    }

    public StringFilter maxCapacity() {
        if (maxCapacity == null) {
            maxCapacity = new StringFilter();
        }
        return maxCapacity;
    }

    public void setMaxCapacity(StringFilter maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public IntegerFilter getNumberOfBeds() {
        return numberOfBeds;
    }

    public IntegerFilter numberOfBeds() {
        if (numberOfBeds == null) {
            numberOfBeds = new IntegerFilter();
        }
        return numberOfBeds;
    }

    public void setNumberOfBeds(IntegerFilter numberOfBeds) {
        this.numberOfBeds = numberOfBeds;
    }

    public IntegerFilter getNumberOfBathrooms() {
        return numberOfBathrooms;
    }

    public IntegerFilter numberOfBathrooms() {
        if (numberOfBathrooms == null) {
            numberOfBathrooms = new IntegerFilter();
        }
        return numberOfBathrooms;
    }

    public void setNumberOfBathrooms(IntegerFilter numberOfBathrooms) {
        this.numberOfBathrooms = numberOfBathrooms;
    }

    public DoubleFilter getRoomRatePerNight() {
        return roomRatePerNight;
    }

    public DoubleFilter roomRatePerNight() {
        if (roomRatePerNight == null) {
            roomRatePerNight = new DoubleFilter();
        }
        return roomRatePerNight;
    }

    public void setRoomRatePerNight(DoubleFilter roomRatePerNight) {
        this.roomRatePerNight = roomRatePerNight;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final RoomTypeCriteria that = (RoomTypeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(type, that.type) &&
            Objects.equals(description, that.description) &&
            Objects.equals(maxCapacity, that.maxCapacity) &&
            Objects.equals(numberOfBeds, that.numberOfBeds) &&
            Objects.equals(numberOfBathrooms, that.numberOfBathrooms) &&
            Objects.equals(roomRatePerNight, that.roomRatePerNight) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updateDate, that.updateDate)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            type,
            description,
            maxCapacity,
            numberOfBeds,
            numberOfBathrooms,
            roomRatePerNight,
            createdBy,
            createdDate,
            updatedBy,
            updateDate
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomTypeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (type != null ? "type=" + type + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (maxCapacity != null ? "maxCapacity=" + maxCapacity + ", " : "") +
            (numberOfBeds != null ? "numberOfBeds=" + numberOfBeds + ", " : "") +
            (numberOfBathrooms != null ? "numberOfBathrooms=" + numberOfBathrooms + ", " : "") +
            (roomRatePerNight != null ? "roomRatePerNight=" + roomRatePerNight + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
            (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
            "}";
    }
}
