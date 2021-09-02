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
 * Criteria class for the {@link com.dd.ttippernest.domain.Photos} entity. This class is used
 * in {@link com.dd.ttippernest.web.rest.PhotosResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /photos?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class PhotosCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter alt;

    private StringFilter caption;

    private StringFilter description;

    private StringFilter href;

    private StringFilter src;

    private StringFilter title;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private InstantFilter updatedBy;

    private InstantFilter updateDate;

    private LongFilter listingId;

    public PhotosCriteria() {}

    public PhotosCriteria(PhotosCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.alt = other.alt == null ? null : other.alt.copy();
        this.caption = other.caption == null ? null : other.caption.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.href = other.href == null ? null : other.href.copy();
        this.src = other.src == null ? null : other.src.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
        this.listingId = other.listingId == null ? null : other.listingId.copy();
    }

    @Override
    public PhotosCriteria copy() {
        return new PhotosCriteria(this);
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

    public StringFilter getAlt() {
        return alt;
    }

    public StringFilter alt() {
        if (alt == null) {
            alt = new StringFilter();
        }
        return alt;
    }

    public void setAlt(StringFilter alt) {
        this.alt = alt;
    }

    public StringFilter getCaption() {
        return caption;
    }

    public StringFilter caption() {
        if (caption == null) {
            caption = new StringFilter();
        }
        return caption;
    }

    public void setCaption(StringFilter caption) {
        this.caption = caption;
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

    public StringFilter getHref() {
        return href;
    }

    public StringFilter href() {
        if (href == null) {
            href = new StringFilter();
        }
        return href;
    }

    public void setHref(StringFilter href) {
        this.href = href;
    }

    public StringFilter getSrc() {
        return src;
    }

    public StringFilter src() {
        if (src == null) {
            src = new StringFilter();
        }
        return src;
    }

    public void setSrc(StringFilter src) {
        this.src = src;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final PhotosCriteria that = (PhotosCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(alt, that.alt) &&
            Objects.equals(caption, that.caption) &&
            Objects.equals(description, that.description) &&
            Objects.equals(href, that.href) &&
            Objects.equals(src, that.src) &&
            Objects.equals(title, that.title) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updateDate, that.updateDate) &&
            Objects.equals(listingId, that.listingId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, alt, caption, description, href, src, title, createdBy, createdDate, updatedBy, updateDate, listingId);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "PhotosCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (alt != null ? "alt=" + alt + ", " : "") +
            (caption != null ? "caption=" + caption + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (href != null ? "href=" + href + ", " : "") +
            (src != null ? "src=" + src + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
            (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
            (listingId != null ? "listingId=" + listingId + ", " : "") +
            "}";
    }
}
