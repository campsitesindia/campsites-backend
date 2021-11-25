package com.dd.campsites.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import javax.validation.constraints.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Photos.
 */
@Entity
@Table(name = "photos")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Photos implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "alt")
    private String alt;

    @Column(name = "caption")
    private String caption;

    @Column(name = "description")
    private String description;

    @Column(name = "href")
    private String href;

    @Column(name = "src")
    private String src;

    @Column(name = "title")
    private String title;

    @Lob
    @Column(name = "image", nullable = false)
    private byte[] image;

    @Column(name = "image_content_type", nullable = false)
    private String imageContentType;

    @Column(name = "is_cover_image")
    private Boolean isCoverImage;

    @Column(name = "height")
    private Integer height;

    @Column(name = "width")
    private Integer width;

    @Column(name = "taken")
    private Instant taken;

    @Column(name = "uploaded")
    private Instant uploaded;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_by")
    private Instant updatedBy;

    @Column(name = "update_date")
    private Instant updateDate;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user" }, allowSetters = true)
    private Album album;

    @ManyToOne
    @JsonIgnoreProperties(value = { "location", "listingType", "owner" }, allowSetters = true)
    private Listing listing;

    @ManyToMany
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JoinTable(name = "rel_photos__tag", joinColumns = @JoinColumn(name = "photos_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    @JsonIgnoreProperties(value = { "photos" }, allowSetters = true)
    private Set<Tag> tags = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Photos id(Long id) {
        this.id = id;
        return this;
    }

    public String getAlt() {
        return this.alt;
    }

    public Photos alt(String alt) {
        this.alt = alt;
        return this;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getCaption() {
        return this.caption;
    }

    public Photos caption(String caption) {
        this.caption = caption;
        return this;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getDescription() {
        return this.description;
    }

    public Photos description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHref() {
        return this.href;
    }

    public Photos href(String href) {
        this.href = href;
        return this;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getSrc() {
        return this.src;
    }

    public Photos src(String src) {
        this.src = src;
        return this;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public String getTitle() {
        return this.title;
    }

    public Photos title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public byte[] getImage() {
        return this.image;
    }

    public Photos image(byte[] image) {
        this.image = image;
        return this;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getImageContentType() {
        return this.imageContentType;
    }

    public Photos imageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
        return this;
    }

    public void setImageContentType(String imageContentType) {
        this.imageContentType = imageContentType;
    }

    public Boolean getIsCoverImage() {
        return this.isCoverImage;
    }

    public Photos isCoverImage(Boolean isCoverImage) {
        this.isCoverImage = isCoverImage;
        return this;
    }

    public void setIsCoverImage(Boolean isCoverImage) {
        this.isCoverImage = isCoverImage;
    }

    public Integer getHeight() {
        return this.height;
    }

    public Photos height(Integer height) {
        this.height = height;
        return this;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public Integer getWidth() {
        return this.width;
    }

    public Photos width(Integer width) {
        this.width = width;
        return this;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Instant getTaken() {
        return this.taken;
    }

    public Photos taken(Instant taken) {
        this.taken = taken;
        return this;
    }

    public void setTaken(Instant taken) {
        this.taken = taken;
    }

    public Instant getUploaded() {
        return this.uploaded;
    }

    public Photos uploaded(Instant uploaded) {
        this.uploaded = uploaded;
        return this;
    }

    public void setUploaded(Instant uploaded) {
        this.uploaded = uploaded;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Photos createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Photos createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedBy() {
        return this.updatedBy;
    }

    public Photos updatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public Photos updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public Album getAlbum() {
        return this.album;
    }

    public Photos album(Album album) {
        this.setAlbum(album);
        return this;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Listing getListing() {
        return this.listing;
    }

    public Photos listing(Listing listing) {
        this.setListing(listing);
        return this;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public Set<Tag> getTags() {
        return this.tags;
    }

    public Photos tags(Set<Tag> tags) {
        this.setTags(tags);
        return this;
    }

    public Photos addTag(Tag tag) {
        this.tags.add(tag);
        tag.getPhotos().add(this);
        return this;
    }

    public Photos removeTag(Tag tag) {
        this.tags.remove(tag);
        tag.getPhotos().remove(this);
        return this;
    }

    public void setTags(Set<Tag> tags) {
        this.tags = tags;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Photos)) {
            return false;
        }
        return id != null && id.equals(((Photos) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Photos{" +
            "id=" + getId() +
            ", alt='" + getAlt() + "'" +
            ", caption='" + getCaption() + "'" +
            ", description='" + getDescription() + "'" +
            ", href='" + getHref() + "'" +
            ", src='" + getSrc() + "'" +
            ", title='" + getTitle() + "'" +
            ", image='" + getImage() + "'" +
            ", imageContentType='" + getImageContentType() + "'" +
            ", isCoverImage='" + getIsCoverImage() + "'" +
            ", height=" + getHeight() +
            ", width=" + getWidth() +
            ", taken='" + getTaken() + "'" +
            ", uploaded='" + getUploaded() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
