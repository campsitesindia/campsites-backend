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
 * Criteria class for the {@link com.dd.campsites.domain.Photos} entity. This class is used
 * in {@link com.dd.campsites.web.rest.PhotosResource} to receive all the possible filtering options from
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

    private BooleanFilter isCoverImage;

    private IntegerFilter height;

    private IntegerFilter width;

    private InstantFilter taken;

    private InstantFilter uploaded;

    private StringFilter createdBy;

    private InstantFilter createdDate;

    private InstantFilter updatedBy;

    private InstantFilter updateDate;

    private LongFilter albumId;

    private LongFilter listingId;

    private LongFilter tagId;

    public PhotosCriteria() {}

    public PhotosCriteria(PhotosCriteria other) {
        this.id = other.id == null ? null : other.id.copy();
        this.alt = other.alt == null ? null : other.alt.copy();
        this.caption = other.caption == null ? null : other.caption.copy();
        this.description = other.description == null ? null : other.description.copy();
        this.href = other.href == null ? null : other.href.copy();
        this.src = other.src == null ? null : other.src.copy();
        this.title = other.title == null ? null : other.title.copy();
        this.isCoverImage = other.isCoverImage == null ? null : other.isCoverImage.copy();
        this.height = other.height == null ? null : other.height.copy();
        this.width = other.width == null ? null : other.width.copy();
        this.taken = other.taken == null ? null : other.taken.copy();
        this.uploaded = other.uploaded == null ? null : other.uploaded.copy();
        this.createdBy = other.createdBy == null ? null : other.createdBy.copy();
        this.createdDate = other.createdDate == null ? null : other.createdDate.copy();
        this.updatedBy = other.updatedBy == null ? null : other.updatedBy.copy();
        this.updateDate = other.updateDate == null ? null : other.updateDate.copy();
        this.albumId = other.albumId == null ? null : other.albumId.copy();
        this.listingId = other.listingId == null ? null : other.listingId.copy();
        this.tagId = other.tagId == null ? null : other.tagId.copy();
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

    public BooleanFilter getIsCoverImage() {
        return isCoverImage;
    }

    public BooleanFilter isCoverImage() {
        if (isCoverImage == null) {
            isCoverImage = new BooleanFilter();
        }
        return isCoverImage;
    }

    public void setIsCoverImage(BooleanFilter isCoverImage) {
        this.isCoverImage = isCoverImage;
    }

    public IntegerFilter getHeight() {
        return height;
    }

    public IntegerFilter height() {
        if (height == null) {
            height = new IntegerFilter();
        }
        return height;
    }

    public void setHeight(IntegerFilter height) {
        this.height = height;
    }

    public IntegerFilter getWidth() {
        return width;
    }

    public IntegerFilter width() {
        if (width == null) {
            width = new IntegerFilter();
        }
        return width;
    }

    public void setWidth(IntegerFilter width) {
        this.width = width;
    }

    public InstantFilter getTaken() {
        return taken;
    }

    public InstantFilter taken() {
        if (taken == null) {
            taken = new InstantFilter();
        }
        return taken;
    }

    public void setTaken(InstantFilter taken) {
        this.taken = taken;
    }

    public InstantFilter getUploaded() {
        return uploaded;
    }

    public InstantFilter uploaded() {
        if (uploaded == null) {
            uploaded = new InstantFilter();
        }
        return uploaded;
    }

    public void setUploaded(InstantFilter uploaded) {
        this.uploaded = uploaded;
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

    public LongFilter getAlbumId() {
        return albumId;
    }

    public LongFilter albumId() {
        if (albumId == null) {
            albumId = new LongFilter();
        }
        return albumId;
    }

    public void setAlbumId(LongFilter albumId) {
        this.albumId = albumId;
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

    public LongFilter getTagId() {
        return tagId;
    }

    public LongFilter tagId() {
        if (tagId == null) {
            tagId = new LongFilter();
        }
        return tagId;
    }

    public void setTagId(LongFilter tagId) {
        this.tagId = tagId;
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
            Objects.equals(isCoverImage, that.isCoverImage) &&
            Objects.equals(height, that.height) &&
            Objects.equals(width, that.width) &&
            Objects.equals(taken, that.taken) &&
            Objects.equals(uploaded, that.uploaded) &&
            Objects.equals(createdBy, that.createdBy) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(updatedBy, that.updatedBy) &&
            Objects.equals(updateDate, that.updateDate) &&
            Objects.equals(albumId, that.albumId) &&
            Objects.equals(listingId, that.listingId) &&
            Objects.equals(tagId, that.tagId)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            alt,
            caption,
            description,
            href,
            src,
            title,
            isCoverImage,
            height,
            width,
            taken,
            uploaded,
            createdBy,
            createdDate,
            updatedBy,
            updateDate,
            albumId,
            listingId,
            tagId
        );
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
            (isCoverImage != null ? "isCoverImage=" + isCoverImage + ", " : "") +
            (height != null ? "height=" + height + ", " : "") +
            (width != null ? "width=" + width + ", " : "") +
            (taken != null ? "taken=" + taken + ", " : "") +
            (uploaded != null ? "uploaded=" + uploaded + ", " : "") +
            (createdBy != null ? "createdBy=" + createdBy + ", " : "") +
            (createdDate != null ? "createdDate=" + createdDate + ", " : "") +
            (updatedBy != null ? "updatedBy=" + updatedBy + ", " : "") +
            (updateDate != null ? "updateDate=" + updateDate + ", " : "") +
            (albumId != null ? "albumId=" + albumId + ", " : "") +
            (listingId != null ? "listingId=" + listingId + ", " : "") +
            (tagId != null ? "tagId=" + tagId + ", " : "") +
            "}";
    }
}
