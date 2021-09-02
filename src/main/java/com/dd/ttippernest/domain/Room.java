package com.dd.ttippernest.domain;

import java.io.Serializable;
import java.time.Instant;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Room.
 */
@Entity
@Table(name = "room")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Room implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "room_number")
    private String roomNumber;

    @Column(name = "is_smoking")
    private String isSmoking;

    @Column(name = "status")
    private String status;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_by")
    private Instant updatedBy;

    @Column(name = "update_date")
    private Instant updateDate;

    @ManyToOne
    private RoomType roomType;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Room id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Room name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRoomNumber() {
        return this.roomNumber;
    }

    public Room roomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
        return this;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getIsSmoking() {
        return this.isSmoking;
    }

    public Room isSmoking(String isSmoking) {
        this.isSmoking = isSmoking;
        return this;
    }

    public void setIsSmoking(String isSmoking) {
        this.isSmoking = isSmoking;
    }

    public String getStatus() {
        return this.status;
    }

    public Room status(String status) {
        this.status = status;
        return this;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Room createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Room createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedBy() {
        return this.updatedBy;
    }

    public Room updatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public Room updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public RoomType getRoomType() {
        return this.roomType;
    }

    public Room roomType(RoomType roomType) {
        this.setRoomType(roomType);
        return this;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Room)) {
            return false;
        }
        return id != null && id.equals(((Room) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Room{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", roomNumber='" + getRoomNumber() + "'" +
            ", isSmoking='" + getIsSmoking() + "'" +
            ", status='" + getStatus() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
