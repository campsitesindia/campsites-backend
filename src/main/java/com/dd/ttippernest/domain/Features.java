package com.dd.ttippernest.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Features.
 */
@Entity
@Table(name = "features")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Features implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "count")
    private Integer count;

    @Column(name = "thumbnail")
    private String thumbnail;

    @Column(name = "icon")
    private String icon;

    @Column(name = "color")
    private String color;

    @Column(name = "img_icon")
    private String imgIcon;

    @Column(name = "description")
    private String description;

    @Column(name = "parent")
    private Integer parent;

    @Column(name = "taxonomy")
    private String taxonomy;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_by")
    private Instant updatedBy;

    @Column(name = "update_date")
    private Instant updateDate;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Features id(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return this.title;
    }

    public Features title(String title) {
        this.title = title;
        return this;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCount() {
        return this.count;
    }

    public Features count(Integer count) {
        this.count = count;
        return this;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getThumbnail() {
        return this.thumbnail;
    }

    public Features thumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
        return this;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getIcon() {
        return this.icon;
    }

    public Features icon(String icon) {
        this.icon = icon;
        return this;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getColor() {
        return this.color;
    }

    public Features color(String color) {
        this.color = color;
        return this;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getImgIcon() {
        return this.imgIcon;
    }

    public Features imgIcon(String imgIcon) {
        this.imgIcon = imgIcon;
        return this;
    }

    public void setImgIcon(String imgIcon) {
        this.imgIcon = imgIcon;
    }

    public String getDescription() {
        return this.description;
    }

    public Features description(String description) {
        this.description = description;
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getParent() {
        return this.parent;
    }

    public Features parent(Integer parent) {
        this.parent = parent;
        return this;
    }

    public void setParent(Integer parent) {
        this.parent = parent;
    }

    public String getTaxonomy() {
        return this.taxonomy;
    }

    public Features taxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
        return this;
    }

    public void setTaxonomy(String taxonomy) {
        this.taxonomy = taxonomy;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Features createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Features createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedBy() {
        return this.updatedBy;
    }

    public Features updatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public Features updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Features)) {
            return false;
        }
        return id != null && id.equals(((Features) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Features{" +
            "id=" + getId() +
            ", title='" + getTitle() + "'" +
            ", count=" + getCount() +
            ", thumbnail='" + getThumbnail() + "'" +
            ", icon='" + getIcon() + "'" +
            ", color='" + getColor() + "'" +
            ", imgIcon='" + getImgIcon() + "'" +
            ", description='" + getDescription() + "'" +
            ", parent=" + getParent() +
            ", taxonomy='" + getTaxonomy() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
