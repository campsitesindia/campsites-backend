package com.dd.campsites.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A RoomsInBooking.
 */
@Entity
@Table(name = "rooms_in_booking")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class RoomsInBooking implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @ManyToOne
    @JsonIgnoreProperties(value = { "user", "listing", "invoices" }, allowSetters = true)
    private Bookings bookings;

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

    public RoomsInBooking id(Long id) {
        this.id = id;
        return this;
    }

    public Bookings getBookings() {
        return this.bookings;
    }

    public RoomsInBooking bookings(Bookings bookings) {
        this.setBookings(bookings);
        return this;
    }

    public void setBookings(Bookings bookings) {
        this.bookings = bookings;
    }

    public Room getRoom() {
        return this.room;
    }

    public RoomsInBooking room(Room room) {
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
        if (!(o instanceof RoomsInBooking)) {
            return false;
        }
        return id != null && id.equals(((RoomsInBooking) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "RoomsInBooking{" +
            "id=" + getId() +
            "}";
    }
}
