package com.dd.campsites.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RoomsForListing.
 */
@Entity
@Table(name = "rooms_for_listing")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoomsForListing implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "listingType", "location", "owner" }, allowSetters = true)
    private Listing listing;

    @ManyToOne
    @JsonIgnoreProperties(value = { "roomType" }, allowSetters = true)
    private Room room;

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public RoomsForListing id(Long id) {
        this.id = id;
        return this;
    }

    public Listing getListing() {
        return this.listing;
    }

    public RoomsForListing listing(Listing listing) {
        this.setListing(listing);
        return this;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public Room getRoom() {
        return this.room;
    }

    public RoomsForListing room(Room room) {
        this.setRoom(room);
        return this;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof RoomsForListing)) {
            return false;
        }
        return id != null && id.equals(((RoomsForListing) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomsForListing{" +
            "id=" + getId() +
            "}";
    }
}
