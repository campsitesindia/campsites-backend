package com.dd.campsites.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FeaturesInRoom.
 */
@Entity
@Table(name = "features_in_room")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FeaturesInRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "roomType" }, allowSetters = true)
    private Room room;

    @ManyToOne
    private Features feature;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public FeaturesInRoom id(Long id) {
        this.id = id;
        return this;
    }

    public Room getRoom() {
        return this.room;
    }

    public FeaturesInRoom room(Room room) {
        this.setRoom(room);
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public Features getFeature() {
        return this.feature;
    }

    public FeaturesInRoom feature(Features features) {
        this.setFeature(features);
        return this;
    }

    public void setFeature(Features features) {
        this.feature = features;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FeaturesInRoom)) {
            return false;
        }
        return id != null && id.equals(((FeaturesInRoom) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FeaturesInRoom{" +
            "id=" + getId() +
            "}";
    }
}
