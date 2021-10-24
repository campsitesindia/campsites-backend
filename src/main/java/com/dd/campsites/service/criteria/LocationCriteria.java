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
 * Criteria class for the {@link com.dd.campsites.domain.Location} entity. This class is used
 * in {@link com.dd.campsites.web.rest.LocationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /locations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
public class LocationCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter title;

    private IntegerFilter count;

    private StringFilter thumbnail;

    private StringFilter icon;

    private StringFilter color;

    private StringFilter imgIcon;

    private StringFilter description;

    private StringFilter taxonomy;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private InstantFilter updatedBy;

    private InstantFilter updateDate;

    private LongFilter parentId;

    public LocationCriteria() {}

    public LocationCriteria(LocationCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.count = other.count == null ? null : other.count.copy();
        this.thumbnail = other.thumbnail == null ? null : other.thumbnail.copy();
        this.icon = other.icon == null ? null : other.icon.copy();
        this.color = other.color == null ? null : other.color.copy();
        this.imgIcon = other.imgIcon == null ? null : other.imgIcon.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.taxonomy = other.taxonomy == null ? null : other.taxonomy.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
        this.parentId = other.parentId == null ? null : other.parentId.copy();
    }

    @Override
    public LocationCriteria copy() {
        return new LocationCriteria(this);
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

    public IntegerFilter getCount() {
        return count;
    }

    public IntegerFilter count() {
        if (count == null) {
            count = new IntegerFilter();
        }
        return count;
    }

    public void setCount(IntegerFilter count) {
        this.count = count;
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

    public StringFilter getIcon() {
        return icon;
    }

    public StringFilter icon() {
        if (icon == null) {
            icon = new StringFilter();
        }
        return icon;
    }

    public void setIcon(StringFilter icon) {
        this.icon = icon;
    }

    public StringFilter getColor() {
        return color;
    }

    public StringFilter color() {
        if (color == null) {
            color = new StringFilter();
        }
        return color;
    }

    public void setColor(StringFilter color) {
        this.color = color;
    }

    public StringFilter getImgIcon() {
        return imgIcon;
    }

    public StringFilter imgIcon() {
        if (imgIcon == null) {
            imgIcon = new StringFilter();
        }
        return imgIcon;
    }

    public void setImgIcon(StringFilter imgIcon) {
        this.imgIcon = imgIcon;
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

    public StringFilter getTaxonomy() {
        return taxonomy;
    }

    public StringFilter taxonomy() {
        if (taxonomy == null) {
            taxonomy = new StringFilter();
        }
        return taxonomy;
    }

    public void setTaxonomy(StringFilter taxonomy) {
        this.taxonomy = taxonomy;
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

    public LongFilter getParentId() {
        return parentId;
    }

    public LongFilter parentId() {
        if (parentId == null) {
            parentId = new LongFilter();
        }
        return parentId;
    }

    public void setParentId(LongFilter parentId) {
        this.parentId = parentId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LocationCriteria that = (LocationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(title, that.title) &&
            Objects.equals(count, that.count) &&
            Objects.equals(thumbnail, that.thumbnail) &&
            Objects.equals(icon, that.icon) &&
            Objects.equals(color, that.color) &&
            Objects.equals(imgIcon, that.imgIcon) &&
            Objects.equals(description, that.description) &&
            Objects.equals(taxonomy, that.taxonomy) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updateDate, that.updateDate) &&
            Objects.equals(parentId, that.parentId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            title,
            count,
            thumbnail,
            icon,
            color,
            imgIcon,
            description,
            taxonomy,
            createdBy,
            createdDate,
            updatedBy,
            updateDate,
            parentId
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationCriteria{" +
            (id != null ? "id=" + id + ", " : "") +
            (title != null ? "title=" + title + ", " : "") +
            (count != null ? "count=" + count + ", " : "") +
            (thumbnail != null ? "thumbnail=" + thumbnail + ", " : "") +
            (icon != null ? "icon=" + icon + ", " : "") +
            (color != null ? "color=" + color + ", " : "") +
            (imgIcon != null ? "imgIcon=" + imgIcon + ", " : "") +
            (description != null ? "description=" + description + ", " : "") +
            (taxonomy != null ? "taxonomy=" + taxonomy + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
            (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
            (parentId != null ? "parentId=" + parentId + ", " : "") +
            "}";
    }
}
