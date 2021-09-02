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
 * Criteria class for the {@link com.dd.ttippernest.domain.Followers} entity. This class is used
 * in {@link com.dd.ttippernest.web.rest.FollowersResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /followers?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class FollowersCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private InstantFilter updatedBy;

    private InstantFilter updateDate;

    private LongFilter followedById;

    private LongFilter userId;

    public FollowersCriteria() {}

    public FollowersCriteria(FollowersCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
        this.followedById = other.followedById == null ? null : other.followedById.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
    }

    @Override
    public FollowersCriteria copy() {
        return new FollowersCriteria(this);
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

    public LongFilter getFollowedById() {
        return followedById;
    }

    public LongFilter followedById() {
        if (followedById == null) {
            followedById = new LongFilter();
        }
        return followedById;
    }

    public void setFollowedById(LongFilter followedById) {
        this.followedById = followedById;
    }

    public LongFilter getUserId() {
        return userId;
    }

    public LongFilter userId() {
        if (userId == null) {
            userId = new LongFilter();
        }
        return userId;
    }

    public void setUserId(LongFilter userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final FollowersCriteria that = (FollowersCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updateDate, that.updateDate) &&
            Objects.equals(followedById, that.followedById) &&
            Objects.equals(userId, that.userId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdBy, createdDate, updatedBy, updateDate, followedById, userId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FollowersCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
            (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
            (followedById != null ? "followedById=" + followedById + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            "}";
    }
}
