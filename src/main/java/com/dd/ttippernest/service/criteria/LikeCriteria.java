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
 * Criteria class for the {@link com.dd.ttippernest.domain.Like} entity. This class is used
 * in {@link com.dd.ttippernest.web.rest.LikeResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /likes?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LikeCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private InstantFilter updatedBy;

    private InstantFilter updateDate;

    private LongFilter postId;

    private LongFilter userId;

    private LongFilter imagesId;

    public LikeCriteria() {}

    public LikeCriteria(LikeCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
        this.postId = other.postId == null ? null : other.postId.copy();
        this.userId = other.userId == null ? null : other.userId.copy();
        this.imagesId = other.imagesId == null ? null : other.imagesId.copy();
    }

    @Override
    public LikeCriteria copy() {
        return new LikeCriteria(this);
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

    public LongFilter getPostId() {
        return postId;
    }

    public LongFilter postId() {
        if (postId == null) {
            postId = new LongFilter();
        }
        return postId;
    }

    public void setPostId(LongFilter postId) {
        this.postId = postId;
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

    public LongFilter getImagesId() {
        return imagesId;
    }

    public LongFilter imagesId() {
        if (imagesId == null) {
            imagesId = new LongFilter();
        }
        return imagesId;
    }

    public void setImagesId(LongFilter imagesId) {
        this.imagesId = imagesId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LikeCriteria that = (LikeCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updateDate, that.updateDate) &&
            Objects.equals(postId, that.postId) &&
            Objects.equals(userId, that.userId) &&
            Objects.equals(imagesId, that.imagesId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, createdBy, createdDate, updatedBy, updateDate, postId, userId, imagesId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LikeCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
            (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
            (postId != null ? "postId=" + postId + ", " : "") +
            (userId != null ? "userId=" + userId + ", " : "") +
            (imagesId != null ? "imagesId=" + imagesId + ", " : "") +
            "}";
    }
}
