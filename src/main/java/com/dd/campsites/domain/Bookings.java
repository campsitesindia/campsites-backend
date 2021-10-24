package com.dd.campsites.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Bookings.
 */
@Entity
@Table(name = "bookings")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Bookings implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "check_in_date")
    private Instant checkInDate;

    @Column(name = "check_out_date")
    private Instant checkOutDate;

    @Column(name = "price_per_night")
    private Double pricePerNight;

    @Column(name = "num_of_nights")
    private Integer numOfNights;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "created_date")
    private Instant createdDate;

    @Column(name = "updated_by")
    private Instant updatedBy;

    @Column(name = "update_date")
    private Instant updateDate;

    @ManyToOne
    private User user;

    @ManyToOne
    @JsonIgnoreProperties(value = { "listingType", "location", "owner" }, allowSetters = true)
    private Listing listing;

    @OneToMany(mappedBy = "bookings")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    @JsonIgnoreProperties(value = { "bookings", "customer" }, allowSetters = true)
    private Set<Invoice> invoices = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bookings id(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return this.name;
    }

    public Bookings name(String name) {
        this.name = name;
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Instant getCheckInDate() {
        return this.checkInDate;
    }

    public Bookings checkInDate(Instant checkInDate) {
        this.checkInDate = checkInDate;
        return this;
    }

    public void setCheckInDate(Instant checkInDate) {
        this.checkInDate = checkInDate;
    }

    public Instant getCheckOutDate() {
        return this.checkOutDate;
    }

    public Bookings checkOutDate(Instant checkOutDate) {
        this.checkOutDate = checkOutDate;
        return this;
    }

    public void setCheckOutDate(Instant checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public Double getPricePerNight() {
        return this.pricePerNight;
    }

    public Bookings pricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
        return this;
    }

    public void setPricePerNight(Double pricePerNight) {
        this.pricePerNight = pricePerNight;
    }

    public Integer getNumOfNights() {
        return this.numOfNights;
    }

    public Bookings numOfNights(Integer numOfNights) {
        this.numOfNights = numOfNights;
        return this;
    }

    public void setNumOfNights(Integer numOfNights) {
        this.numOfNights = numOfNights;
    }

    public String getCreatedBy() {
        return this.createdBy;
    }

    public Bookings createdBy(String createdBy) {
        this.createdBy = createdBy;
        return this;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Instant getCreatedDate() {
        return this.createdDate;
    }

    public Bookings createdDate(Instant createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getUpdatedBy() {
        return this.updatedBy;
    }

    public Bookings updatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
        return this;
    }

    public void setUpdatedBy(Instant updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Instant getUpdateDate() {
        return this.updateDate;
    }

    public Bookings updateDate(Instant updateDate) {
        this.updateDate = updateDate;
        return this;
    }

    public void setUpdateDate(Instant updateDate) {
        this.updateDate = updateDate;
    }

    public User getUser() {
        return this.user;
    }

    public Bookings user(User user) {
        this.setUser(user);
        return this;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Listing getListing() {
        return this.listing;
    }

    public Bookings listing(Listing listing) {
        this.setListing(listing);
        return this;
    }

    public void setListing(Listing listing) {
        this.listing = listing;
    }

    public Set<Invoice> getInvoices() {
        return this.invoices;
    }

    public Bookings invoices(Set<Invoice> invoices) {
        this.setInvoices(invoices);
        return this;
    }

    public Bookings addInvoice(Invoice invoice) {
        this.invoices.add(invoice);
        invoice.setBookings(this);
        return this;
    }

    public Bookings removeInvoice(Invoice invoice) {
        this.invoices.remove(invoice);
        invoice.setBookings(null);
        return this;
    }

    public void setInvoices(Set<Invoice> invoices) {
        if (this.invoices != null) {
            this.invoices.forEach(i -> i.setBookings(null));
        }
        if (invoices != null) {
            invoices.forEach(i -> i.setBookings(this));
        }
        this.invoices = invoices;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Bookings)) {
            return false;
        }
        return id != null && id.equals(((Bookings) o).id);
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Bookings{" +
            "id=" + getId() +
            ", name='" + getName() + "'" +
            ", checkInDate='" + getCheckInDate() + "'" +
            ", checkOutDate='" + getCheckOutDate() + "'" +
            ", pricePerNight=" + getPricePerNight() +
            ", numOfNights=" + getNumOfNights() +
            ", createdBy='" + getCreatedBy() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            ", updatedBy='" + getUpdatedBy() + "'" +
            ", updateDate='" + getUpdateDate() + "'" +
            "}";
    }
}
